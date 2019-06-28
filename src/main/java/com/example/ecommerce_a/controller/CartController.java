package com.example.ecommerce_a.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
		if (loginUser != null) {
			User user = loginUser.getUser();
			order = orderService.showShoppingCart(user.getId());
		} else {
			order = (Order) session.getAttribute("order");
		}

		if (order == null) {
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
	public String deleteItem(Integer orderItemId, Integer totalPrice, Integer orderId,
			@AuthenticationPrincipal LoginUser loginUser) {

		if (loginUser != null) {
			orderService.deleteByOrderItem(orderItemId, totalPrice, orderId);
		} else {
			Order order = (Order) session.getAttribute("order");
			List<OrderItem> orderItemList = order.getOrderItemList();
			for (int i = 0; i < orderItemList.size(); i++) {
				if (orderItemList.get(i).getId() == orderItemId) {
					orderItemList.remove(i);
				}
			}
			order.setTotalPrice(order.getTotalPrice() - totalPrice);
			if (order.getOrderItemList().size() == 0) {
				session.removeAttribute("order");
			}
		}

		
		return "redirect:/cart/showCart";
	}

	/**
	 * 購入履歴を表示する.
	 * 
	 * @param model     購入履歴
	 * @param loginUser ログイン情報
	 * @return 購入履歴表示
	 */
	@RequestMapping("/showHistory")
	public String showHistory(Model model, @AuthenticationPrincipal LoginUser loginUser) {

		if (loginUser == null) {
			return "redirect:/toLogin/";
		}
		User user = loginUser.getUser();

		List<Order> orderList = orderService.showShoppingHistory(user.getId());
		if (orderList == null) {
			model.addAttribute("message", "履歴は一件もありません");
		} else {
			Collections.reverse(orderList);
			model.addAttribute("orderList", orderList);
		}
		
		return "order-history";

	}

	/**
	 * 再注文する.
	 * 
	 * @param orderItemId : 注文商品ID
	 * @param loginUser   : ログイン情報
	 * @return ショッピングカートページ
	 */
	@RequestMapping("/repurchase")
	public String showRepurchase(Integer orderItemId, @AuthenticationPrincipal LoginUser loginUser) {
		User user = loginUser.getUser();
		OrderItem orderItem = orderService.showOrderItem(orderItemId);
		orderItem.setId(null);
		Order order = orderService.showShoppingCart(user.getId());
		if (order == null) {
			order = new Order();
			order.setTotalPrice(0);
			order.setUser(user);
			order.setUserId(user.getId());
		}
		order.setStatus(Order.Status.BEFORE_ORDER.getCode());
		List<OrderItem> orderItemList = new ArrayList<>();
		orderItemList.add(orderItem);
		order.setOrderItemList(orderItemList);
		order.setTotalPrice(orderItem.getSubTotal());
		orderService.addItemToCart(order);
		
		return "redirect:/cart/showCart";
	}

	/**
	 * 非同期で注文商品を更新する.
	 * 
	 * @param orderItemId 注文商品ID
	 * @param quantity    変更する数量
	 * @return ショッピングカート表示
	 */
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, String> updateOrderItem(Integer orderItemId, Integer quantity,
			@AuthenticationPrincipal LoginUser loginUser) {
		Order order = null;
		OrderItem orderItem = null;

		if (loginUser == null) {
			order = (Order) session.getAttribute("order");
			for (OrderItem oi : order.getOrderItemList()) {
				if (oi.getId() == orderItemId) {
					orderItem = oi;
					break;
				}
			}
			order.setTotalPrice(order.getTotalPrice() - orderItem.getSubTotal());
			orderItem.setQuantity(quantity);
			order.setTotalPrice(order.getTotalPrice() + orderItem.getSubTotal());
		} else {
			orderItem = orderService.showOrderItem(orderItemId);
			orderItem.setQuantity(quantity);
			orderService.updateByOrderItem(orderItem);
			order = orderService.showShoppingCart(loginUser.getUser().getId());
		}
		Map<String, String> map = new HashMap<>();
		map.put("calcTotalPrice", String.format("%,d", order.getCalcTotalPrice()));
		map.put("tax", String.format("%,d", order.getTax()));
		map.put("subTotal", String.format("%,d", orderItem.getSubTotal()));

		return map;
	}

	/**
	 * 商品をキャンセルする.
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/cancel")
	public String cancel(Integer orderId) {

		Order order = orderService.load(orderId);
		if (order != null && order.getStatus() != Order.Status.DONE_DELIVELY.getCode()) {
			order.setStatus(Order.Status.CANCEL.getCode());
			orderService.update(order);
		}

		return "redirect:/cart/showHistory";
	}

}
