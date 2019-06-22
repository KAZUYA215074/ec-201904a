package com.example.ecommerce_a.controller;

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
		
		if(order==null || order.getOrderItemList().get(0).getId()==0) {
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
		}
		
		return "redirect:/cart/showCart";
	}
}
