package com.example.ecommerce_a.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.LoginUser;
import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.OrderTopping;
import com.example.ecommerce_a.domain.Topping;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.form.OrderItemForm;
import com.example.ecommerce_a.repository.ItemRepository;
import com.example.ecommerce_a.service.ItemService;
import com.example.ecommerce_a.service.OrderService;
import com.example.ecommerce_a.service.ToppingService;

/**
 * ピザの詳細を表示するコントローラ.
 * 
 * @author Makoto
 *
 */
@Controller
@RequestMapping("/detail")
public class ItemDetailController {

	@Autowired
	private HttpSession session;

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ToppingService toppingService;

	// トッピングを１列に表示する個数
	private static final int MAX_COLS = 3;

	@ModelAttribute
	public OrderItemForm setUpOrderItemForm() {
		return new OrderItemForm();
	}

	/**
	 * ピザの詳細を表示する.
	 * 
	 * @param id    ピザのid
	 * @param model モデル
	 * @return ピザの詳細ページ
	 */
	@RequestMapping("")
	public String showDetail(Integer id, Model model) {
		Item item = itemService.load(id);
		List<Topping> toppings = toppingService.findAll();
		item.setToppingList(toppings);
		List<Topping> splitedToppingList = new ArrayList<>();

		List<List<Topping>> splitedToppingListInList = new ArrayList<>();
		for (int i = 0; i < toppings.size(); i++) {
			splitedToppingList.add(toppings.get(i));
			if ((i + 1) % MAX_COLS == 0) {
				splitedToppingListInList.add(splitedToppingList);
				splitedToppingList = new ArrayList<>();
			}
		}
		model.addAttribute("item", item);
		model.addAttribute("splitedToppingListInList", splitedToppingListInList);
		
		return "item_detail";
	}

	/**
	 * カートに追加する.
	 * @param form フォーム
	 * @return 商品一覧
	 */
	@RequestMapping("/addItem")
	public String addItemToCart(
			OrderItemForm form,
			@AuthenticationPrincipal LoginUser loginUser
			) {
		
		User user = null;
		try {
			user = loginUser.getUser();
		} catch (NullPointerException e) {
			user = null;
		}
		
		// 注文トッピングリスト
		List<OrderTopping> orderToppingList = new ArrayList<>();
		if(form.getToppingIdList()!=null) {
			for (Integer id: form.getToppingIdList()) {
				OrderTopping orderTopping = new OrderTopping();
				Topping topping = toppingService.load(id);
				orderTopping.setTopping(topping);
				orderTopping.setToppingId(topping.getId());;
				orderToppingList.add(orderTopping);
			}
		}
		
		// 注文商品
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderToppingList(orderToppingList);
		orderItem.setQuantity(form.getQuantity());
		orderItem.setSize(form.getSize());
		orderItem.setItem(itemRepository.load(form.getItemId()));
		orderItem.setItemId(form.getItemId());
		
		// 注文
		Order order = null;
		List<OrderItem> orderItemList = null;
		
		if(user!=null) {
			order = new Order();
			order.setUserId(user.getId());
			orderItemList = new ArrayList<>();
		} else {
			order = (Order) session.getAttribute("order");
			
			if(order==null) {
				order = new Order();
				session.setAttribute("order", order);
				orderItemList = new  ArrayList<>();
			} else {
				orderItemList = order.getOrderItemList();
			}
			
		}
		order.setOrderItemList(orderItemList);
		orderItemList.add(orderItem);
		order.setStatus(Order.Status.BEFORE_ORDER.getCode());
		int totalPrice = 0;
		for(OrderItem localOrderItem:orderItemList) {
			totalPrice += localOrderItem.getSubTotal();
		}
		order.setTotalPrice(totalPrice);
		
		if(user!=null) {
			orderService.addItemToCart(order);
		}
		return "redirect:/";
	}
}
