package com.example.ecommerce_a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.repository.ItemRepository;

@Service
@Transactional
public class ItemService {
	@Autowired
	private ItemRepository itemRepository;
	
	public List<Item> findAll(){
		return itemRepository.findAll();
	}
	
	public List<Item> findByName(String name,Integer offset){
		if(offset == null) {
			offset = 1;
		}
		offset = offset *10 - 9;
		return itemRepository.findByName(name,offset);
	}
	
	public List<String> itemAllName(){
		return itemRepository.itemAllName();
	}
	
	public Item load(Integer id) {
		return itemRepository.load(id);
	}
	
	public List<Item> sort(String sort,Integer offset){
		if(offset == null) {
			offset = 1;
		}
		offset = offset *10 - 9;
		return itemRepository.sort(sort,offset);
	}
}
