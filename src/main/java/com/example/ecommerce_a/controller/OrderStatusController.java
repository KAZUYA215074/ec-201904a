package com.example.ecommerce_a.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.Order.Status;
import com.example.ecommerce_a.form.StatusSortForm;
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
	@ModelAttribute
	private StatusSortForm setUpFrom() {
		return new StatusSortForm();
	}
	
	/**
	 * 注文状況一覧を表示する.
	 * 
	 * @param model モデル
	 * @param sort ソートする情報(orderDate||deliveryTime)	<-- セレクトタグ
	 * @param statuses 注文状況(1 or 2 or 3)				<-- チェックボックス
	 * @return 注文状況一覧ページ
	 */
	@RequestMapping("/showStatus")
	public String showStatus(Model model,StatusSortForm form) {
		List<Order> orderList = null;
		if(form.getStatuses()==null || form.getStatuses().length==0) {
			orderList = orderService.showOrderByStatus();
		}else {
			List<Integer> statusList = new ArrayList<>();
			for(Integer status:form.getStatuses()) {
				statusList.add(status);
			}
			orderList = orderService.showOrderByStatus(statusList,form.getSortName());
		}
		if(orderList==null) {
			model.addAttribute("notFound","一致する注文はありません");
		}
		model.addAttribute("orderList",orderList);
		
		List<Status> statusList = new ArrayList<>();
		statusList.add(Status.NOT_PAYMENT);
		statusList.add(Status.DONE_PAYMENT);
		statusList.add(Status.DONE_DELIVELY);
		model.addAttribute("statusList",statusList);
		
		
		return "admin-order-status";
	}
	
	@RequestMapping("/update")
	public String update(Integer orderId,Integer status) {
		if(orderId==null||status==null||status==0) {
			return "redirect:/orderStatus/showStatus";
		}
		Order order = orderService.load(orderId);
		order.setStatus(status);
		orderService.update(order);
		return "redirect:/orderStatus/showStatus";
	}
}
