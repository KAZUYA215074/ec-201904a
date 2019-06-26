package com.example.ecommerce_a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.repository.ItemRepository;

/**
 * Itemを操作するサービスクラス.
 * 
 * @author sho.ikehara
 *
 */
@Service
@Transactional
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	/**
	 * 全件検索.
	 * 
	 * @return　商品リスト
	 */
	public List<Item> findAll(){
		return itemRepository.findAll();
	}
	
	/**
	 * あいまい検索.
	 * 
	 * @param name 入力された文字列
	 * @param offset　データの開始位置
	 * @return　商品リスト
	 */
	public List<Item> findByName(String name,String sort){
		return itemRepository.findByName(name,sort);
	}
	
	/**
	 * 商品の名前一覧.
	 * 
	 * @return 商品の名前一覧
	 */
	public List<String> itemAllName(){
		return itemRepository.itemAllName();
	}
	
	/**
	 * id検索を行う.
	 * 
	 * @param id　商品ID
	 * @return　商品詳細
	 */
	public Item load(Integer id) {
		return itemRepository.load(id);
	}
	
}