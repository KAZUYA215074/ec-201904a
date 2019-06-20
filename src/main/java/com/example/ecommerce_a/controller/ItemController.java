package com.example.ecommerce_a.controller;

import java.util.ArrayList;
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
	private static final int MAX_COLS = 3;
	private static final int ELEMENT_COUNT = 9;
	
	@RequestMapping("/showList")
	public String showList(Model model,String name,Integer page) {
		if(name==null) {
			name="";
		}
		List<Integer> pageList = new ArrayList<>();
		for(int i = 1;i <(itemService.findAll().size()/ELEMENT_COUNT)+1;i++) {
			pageList.add(i);
		}
		List<Item> itemList = itemService.findByName(name,page);
		List<Item> list = new ArrayList<>();
		List<List<Item>> listList = new ArrayList<>();
		for(int i=0;i<itemList.size();i++) {
			list.add(itemList.get(i));
			if((i+1)%MAX_COLS==0) {
				listList.add(list);
				list = new ArrayList<>();				
			}
		}
		model.addAttribute("listList",listList);
		model.addAttribute("pageList",pageList);
		return "item_list";
	}
	
	@RequestMapping("/sort")
	public String sort(Model model, String sort,Integer page) {
		List<Integer> pageList = new ArrayList<>();
		for(int i = 1;i <(itemService.findAll().size()/ELEMENT_COUNT)+1;i++) {
			pageList.add(i);
		}
		List<Item> itemList = itemService.sort(sort,page);
		List<Item> list = new ArrayList<>();
		List<List<Item>> listList = new ArrayList<>();
		for(int i=0;i<itemList.size();i++) {
			list.add(itemList.get(i));
			if((i+1)%MAX_COLS==0) {
				listList.add(list);
				list = new ArrayList<>();				
			}
		}
		model.addAttribute("listList",listList);
		model.addAttribute("pageList",pageList);
		return "item_list";
	}
}
