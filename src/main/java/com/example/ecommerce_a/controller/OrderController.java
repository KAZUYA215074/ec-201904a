package com.example.ecommerce_a.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.CreditcardInfo;
import com.example.ecommerce_a.domain.LoginUser;
import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.Order.PaymentMethod;
import com.example.ecommerce_a.domain.ResponceCreditcardServerInfo;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.form.OrderForm;
import com.example.ecommerce_a.service.OrderService;
import com.example.ecommerce_a.service.PostWebAPIService;
import com.example.ecommerce_a.utils.ConvertUtils;
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

	@Autowired
	PostWebAPIService postWebAPIService;

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
	public String toOrder(Model model,OrderForm form, @AuthenticationPrincipal LoginUser loginUser) {
		Order order = orderService.showShoppingCart((loginUser.getUser().getId()));
		List<Integer> months = new ArrayList<>();
		List<Integer> years = new ArrayList<>();
		int minYear = LocalDate.now().getYear();
		int maxYear = LocalDate.now().plusYears(10).getYear();
		for (int i = 1; i <= 12; i++) {
			months.add(i);
		}
		for (int i = minYear; i <= maxYear; i++) {
			years.add(i);
		}
		model.addAttribute("order", order);
		model.addAttribute("years", years);
		model.addAttribute("months", months);
		User user = loginUser.getUser();
		form.setDestinationEmail(user.getMailAddress());
		//form.setDestinationTel(user.getTelephone());
		form.setDestinationAddress(user.getAddress());
		form.setDestinationZipcode(user.getZipCode());
		form.setDestinationName(user.getName());
		form.setDeliveryDate(Date.valueOf(LocalDate.now()).toString());
		
		return "order_confirm";
	}

	/**
	 * 注文情報を更新する.
	 * 
	 * @param form
	 * @return 注文完了ページ
	 */
	@RequestMapping("/ordercomp")
	public String order(@Validated OrderForm form, BindingResult result, Model model,
			@AuthenticationPrincipal LoginUser loginUser) {
		User user = loginUser.getUser();
		Order order = orderService.showShoppingCart(user.getId());
		order.setPaymentMethod(form.getIntPaymentMethod());

		if (order.getPaymentMethod() == PaymentMethod.CREDIT.getCode()) {
			CreditcardInfo creditcardInfo = form.createCreditcardInfo();
			creditcardInfo.setUser_id(user.getId());
			creditcardInfo.setOrder_number(String.valueOf(order.getId()));
			creditcardInfo.setAmount(String.valueOf(order.getTotalPrice()));
			// if cvv is 123, return error.
			ResponceCreditcardServerInfo response = postWebAPIService.postCreditcardServer(creditcardInfo);

			if (response.getStatus().equals("error")) {
				result.rejectValue("cardNumber", null, "クレジットカード情報が不正です");
			}
		}

		if (result.hasErrors()) {
			System.out.println(result.getAllErrors().get(0).getDefaultMessage());
			return toOrder(model,form,loginUser);
		}

		BeanUtils.copyProperties(form, order);
		order.setOrderDate(Date.valueOf(LocalDate.now()));
		order.setDestinationZipcode(ConvertUtils.getDelHyphenZipCode(form.getDestinationZipcode()));
		order.setDestinationTel(ConvertUtils.getHypehnTelephone(form.getDestinationTel()));
		order.setDeliveryTime(Timestamp.valueOf(form.getDeliveryDate() + " " + form.getDeliveryTime() + ":00:00"));

		if (order.getPaymentMethod() == Order.PaymentMethod.CASH_ON_DELIVERY.getCode()) {
			order.setStatus(Order.Status.NOT_PAYMENT.getCode());
		} else if (order.getPaymentMethod() == Order.PaymentMethod.CREDIT.getCode()) {
			order.setStatus(Order.Status.DONE_PAYMENT.getCode());
		}

		orderService.update(order);

//		sendMail.sendMainForOrderConfirmation(order);
		//sendMail.sendMailHTML(order);
		return "order_finished";
	}

}
