package com.example.ecommerce_a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.form.OrderForm;
import com.example.ecommerce_a.service.OrderService;


/**
 * 
 * 注文情報を操作するコントローラ.
 * @author risa.nazato
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	OrderService orderService;
	
	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public OrderForm setUpOrderForm() {
		return new OrderForm();
	}
	

}
