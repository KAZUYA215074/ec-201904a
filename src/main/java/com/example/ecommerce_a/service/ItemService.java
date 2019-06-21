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
	public List<Item> findByName(String name,Integer offset){
		if(offset == null) {
			offset = 1;
		}
		offset = offset *10 - 9;
		return itemRepository.findByName(name,offset);
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
	
	/**
	 * 任意の順にソートを行う
	 * 
	 * @param sort　ソートするテーブルのカラム名
	 * @param offset　データの開始位置
	 * @return　ソートされた商品一覧
	 */
	public List<Item> sort(String sort,Integer offset){
		if(offset == null) {
			offset = 1;
		}
		offset = offset *10 - 9;
		return itemRepository.sort(sort,offset);
	}
}