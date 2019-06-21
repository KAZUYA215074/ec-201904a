package com.example.ecommerce_a.controller;


import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.form.OrderForm;
import com.example.ecommerce_a.service.OrderService;

/**
 * 
 * 注文情報を操作するコントローラ.
 * 
 * @author risa.nazato
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	OrderService orderService;

//	@Autowired
//	private HttpSession session;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public OrderForm setUpOrderForm() {
		return new OrderForm();
	}

	/**
	 * 注文確認画面を表示する
	 * 
	 * @param OrderId 注文ID
	 * @param model   Model
	 * @return 注文確認ページ
	 */
	@RequestMapping("/orderlist")
	public String toOrder(Model model) {
		int orderId = 1;
		System.out.println(orderId);
		Order order = orderService.load(orderId);
		model.addAttribute("order", order);
		return "order_confirm";
	}

	/**
	 * 注文情報を更新する.
	 * @param form
	 * @return 注文完了ページ
	 */
	@RequestMapping("/ordercomp")
	public String order(@Validated OrderForm form, BindingResult result,Model model) {
		if(result.hasErrors()) {
			System.out.println("error");
			return toOrder(model);
		}
	
		Order order = new Order();
		order.setId(form.getId());
		order.setUserId(form.getIntuserId());
		order.setStatus(form.getIntStatus());
		order.setTotalPrice(form.getIntTotalPrice());
		order.setOrderDate(Date.valueOf(form.getOrderDate()));
		order.setDestinationName(form.getDestinationName());
		order.setDestinationEmail(form.getDestinationEmail());
		order.setDestinationZipcode(form.getDestinationZipcode());
		order.setDestinationAddress(form.getDestinationAddress());
		order.setDestinationTel(form.getDestinationTel());
		order.setDeliveryTime(form.getDeliveryTime());
		order.setPaymentMethod(form.getIntPaymentMethod());
		orderService.update(order);
		return "order_finished";
	}

}
