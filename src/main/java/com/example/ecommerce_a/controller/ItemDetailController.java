package com.example.ecommerce_a.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.Topping;
import com.example.ecommerce_a.service.ItemService;
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
	private ToppingService toppingService;

	// トッピングを１列に表示する個数
	private static final int MAX_COLS = 3;

	/**
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
}
