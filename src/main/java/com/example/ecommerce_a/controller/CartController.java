package com.example.ecommerce_a.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.LoginUser;
import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.service.OrderService;

/**
 * ショッピングカートのコントローラー.
 * 
 * @author taka
 *
 */
@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private HttpSession session;

	/**
	 * カートの中身を表示する.
	 * 
	 * @param model : ショッピングカート情報
	 * @return ショッピングカートリストページ
	 */
	@RequestMapping("/showCart")
	public String showCartList(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		
		Order order = null;
		if(loginUser!=null) {
			User user = loginUser.getUser();
			order = orderService.showShoppingCart(user.getId());
		} else {
			order = (Order) session.getAttribute("order");
		}
		
		if(order==null) {
			return "cart_list_not";
		}
		model.addAttribute("order", order);
		return "cart_list";
	}
	
	/**
	 * 商品を削除する.
	 * 
	 * @param id 削除するのID
	 * @return ショッピングカートリスト表示
	 */
	@RequestMapping("/delete")
	public String deleteItem(
			Integer orderItemId,Integer totalPrice,Integer orderId, 
			@AuthenticationPrincipal LoginUser loginUser) {
		
		if(loginUser!=null) {
			orderService.deleteByOrderItem(orderItemId,totalPrice,orderId);
		}else {
			Order order = (Order) session.getAttribute("order");
			List<OrderItem> orderItemList = order.getOrderItemList();
			for(int i=0;i<orderItemList.size();i++) {
				if(orderItemList.get(i).getId()==orderItemId) {
					orderItemList.remove(i);
				}
			}
			order.setTotalPrice(order.getTotalPrice()-totalPrice);
			if(order.getOrderItemList().size() == 0) {
				session.removeAttribute("order");
			}
		}
		
		return "redirect:/cart/showCart";
	}
	
	/**
	 * 購入履歴を表示する.
	 * 
	 * @param model 購入履歴
	 * @param loginUser ログイン情報
	 * @return 購入履歴表示
	 */
	@RequestMapping("/showHistory")
	public String showHistory(Model model,@AuthenticationPrincipal LoginUser loginUser) {
		
		if(loginUser == null) {
			return "redirect:/toLogin/";
		}
		User user = loginUser.getUser();
		
		List<Order> orderList = orderService.showShoppingHistory(user.getId());
		Collections.reverse(orderList);
		model.addAttribute("orderList",orderList);
		return "order-history";
		
	}
	
	/**
	 * 再注文する.
	 * 
	 * @param orderItemId : 注文商品ID
	 * @param loginUser : ログイン情報
	 * @return ショッピングカートページ
	 */
	@RequestMapping("/repurchase")
	public String showRepurchase(Integer orderItemId,@AuthenticationPrincipal LoginUser loginUser) {
		User user = loginUser.getUser();
		OrderItem orderItem = orderService.showOrderItem(orderItemId);
		Order order = orderService.showShoppingCart(user.getId());
		if(order == null) {
			order = new Order();
			order.setTotalPrice(orderItem.getSubTotal());
			order.setUser(user);
			order.setUserId(user.getId());
		}
		order.setStatus(Order.Status.BEFORE_ORDER.getCode());
		List<OrderItem> orderItemList = new ArrayList<>();
		orderItemList.add(orderItem);
		order.setOrderItemList( orderItemList ); 
		orderService.addItemToCart(order);
		
		return "redirect:/cart/showCart";
	}
}
