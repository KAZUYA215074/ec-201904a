package com.example.ecommerce_a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.repository.ItemRepository;

/**
 * アイテムを操作するサービス.
 * 
 * @author Makoto
 *
 */
@Service
@Transactional
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	/**
	 * ピザを全検検索する
	 * 
	 * @return ピザのリスト
	 */
	public List<Item> findAll(){
		return itemRepository.findAll();
	}
	
	/**
	 * ピザをあいまい検索する.
	 * @param name ピザの名前
	 * @return ピザのリスト
	 */
	public List<Item> findByName(String name){
		return itemRepository.findByName(name);
	}
	
<<<<<<< HEAD
	public List<String> itemAllName(){
		return itemRepository.itemAllName();
	}
	
=======
	/**
	 * ピザの主キー検索.
	 * 
	 * @param id ピザのid
	 * @return ピザ
	 */
>>>>>>> 171da4b283c507be8ceaa7864d4b9d8a7d1fce52
	public Item load(Integer id) {
		return itemRepository.load(id);
	}
}
