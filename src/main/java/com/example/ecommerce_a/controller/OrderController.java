package com.example.ecommerce_a.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.form.OrderForm;
import com.example.ecommerce_a.service.OrderService;

import jp.co.sample.emp_management.domain.Employee;


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
	
	/**
	 * 従業員一覧画面を出力します.
	 * 
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Order> orderList = orderService.showList();
		String allName = employeeService.autoComplete();
		model.addAttribute("employeeList", employeeList);
		model.addAttribute("allName", allName);
		return "employee/list";
	}

}
