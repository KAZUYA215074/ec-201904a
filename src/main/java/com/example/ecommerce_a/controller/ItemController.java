package com.example.ecommerce_a.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	ItemService itemService;
	
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Item> itemList = itemService.findAll();
		model.addAttribute("itemList",itemList);
		return "item_list";
	}
	
	@RequestMapping("/findByName")
	public String findByName(String name,Model model) {
		List<Item> itemList = itemService.findByName(name);
		model.addAttribute("itemList",itemList);
		return "item_list";
	}
}
