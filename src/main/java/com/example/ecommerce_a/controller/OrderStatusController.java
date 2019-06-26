package com.example.ecommerce_a.controller;

import java.util.ArrayList;
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
	
	/**
	 * 注文状況一覧を表示する.
	 * 
	 * @param model モデル
	 * @param sort ソートする情報(orderDate||deliveryTime)	<-- セレクトタグ
	 * @param statuses 注文状況(1 or 2 or 3)				<-- チェックボックス
	 * @return 注文状況一覧ページ
	 */
	@RequestMapping("/showStatus")
	public String showStatus(Model model,String sort,Integer[] statuses) {
		List<Order> orderList = null;
		if(statuses==null || statuses.length==0) {
			orderList = orderService.showOrderByStatus();
		}else {
			List<Integer> statusList = new ArrayList<>();
			for(Integer status:statuses) {
				statusList.add(status);
			}
			orderList = orderService.showOrderByStatus(statusList,sort);
		}
		model.addAttribute("orderList",orderList);
		
		return "admin-order-status";
	}
	


}
