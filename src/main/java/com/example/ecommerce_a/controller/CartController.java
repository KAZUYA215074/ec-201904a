package com.example.ecommerce_a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Order;
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
	
	//TODO:テスト必要ショッピングカート表示
	/**
	 * カートの中身を表示する.
	 * 
	 * @param model : ショッピングカート情報
	 * @return ショッピングカートリストページ
	 */
	@RequestMapping("/showCart")
	public String showCartList(Model model) {
		
		User user = new User();
		user.setId(1);
		Order order = orderService.showShoppingCart(user.getId());
		model.addAttribute("order", order);
		return "cart_list";
	}
	
	//TODO:テスト必要ショッピングカート削除
	/**
	 * 商品を削除する.
	 * 
	 * @param id 削除するのID
	 * @return ショッピングカートリスト表示
	 */
	@RequestMapping("/delete")
	public String deleteItem(Integer id) {
		orderService.deleteById(id);
		return "redirect:/cart/showCart";
	}
}
