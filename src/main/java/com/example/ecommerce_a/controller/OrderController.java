package com.example.ecommerce_a.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.LoginUser;
import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.form.OrderForm;
import com.example.ecommerce_a.service.OrderService;
import com.example.ecommerce_a.utils.SendMail;

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
	private OrderService orderService;
	
	@Autowired
	private SendMail sendMail;

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
	public String toOrder(Model model,@AuthenticationPrincipal LoginUser loginUser) {
		Order order = orderService.showShoppingCart((loginUser.getUser().getId()));
		model.addAttribute("order", order);
		return "order_confirm";
	}

	/**
	 * 注文情報を更新する.
	 * 
	 * @param form
	 * @return 注文完了ページ
	 */
	@RequestMapping("/ordercomp")
	public String order(@Validated OrderForm form, BindingResult result, Model model,@AuthenticationPrincipal LoginUser loginUser) {
		if (result.hasErrors()) {
			return toOrder(model,loginUser);
		}
		User user = loginUser.getUser();
		Order order = orderService.showShoppingCart(user.getId());
		order.setOrderDate(Date.valueOf(LocalDate.now()));
		order.setDestinationName(form.getDestinationName());
		order.setDestinationEmail(form.getDestinationEmail());
		order.setDestinationZipcode(form.getDestinationZipcode());
		order.setDestinationAddress(form.getDestinationAddress());
		order.setDestinationTel(form.getDestinationTel());
		order.setDeliveryTime(Timestamp.valueOf(form.getDeliveryDate() + " " + form.getDeliveryTime()+":00:00"));
		order.setPaymentMethod(form.getIntPaymentMethod());
		orderService.update(order);
		
		//sendMail.sendMainForOrderConfirmation(order);
		
		return "order_finished";
	}

}
