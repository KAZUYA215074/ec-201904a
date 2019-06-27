package com.example.ecommerce_a.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.LoginUser;
import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.service.OrderService;

/**
 * 共通のコントローラー.
 * 
 * @author taka
 *
 */
@Controller
@RequestMapping("/common")
public class CommonController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private HttpSession session;

	/**
	 * ログイン後に遷移するページを選択する.
	 * 
	 * @param loginUser ログイン情報
	 * @return ログイン後のページ
	 */
	@RequestMapping("/afterLogin")
	public String afterLogin(@AuthenticationPrincipal LoginUser loginUser) {
		User user = loginUser.getUser();
		Order order = (Order) session.getAttribute("order");
		if (order != null) {
			order.setUser(user);
			order.setUserId(user.getId());
			orderService.addItemToCart(order);
		}
		session.removeAttribute("order");

		String referer = (String) session.getAttribute("referer");

		if (referer != null) {
			String path = referer.replaceAll("(http.?:\\/\\/[a-zA-Z0-9.:]*)", "");
			if (path.equals("/ec-201904a/cart/showCart") && order != null) {
				return "redirect:/order/orderlist";
			}
		}

		return "redirect:/";
	}

}
