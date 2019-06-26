package com.example.ecommerce_a.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.service.OrderService;

/**
 * 注文状況を表示する.
 * 
 * @author taka
 *
 */
@Controller
@RequestMapping("/orderStatus")
public class OrderStatusController {
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/showStatus")
	public String showStatus(Model model) {
		List<Order> orderList = orderService.showOrderByStatus();
		model.addAttribute("orderList",orderList);
		
		return "admin-order-status";
	}

}
