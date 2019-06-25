package com.example.ecommerce_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.repository.ProductManagementRepository;

/**
 * 新商品追加の操作を行う.
 *
 * @author yuki
 *
 */
@Service
@Transactional
public class ProductManagementService {
	
	@Autowired
	private ProductManagementRepository repository;
	
	/**
	 * 新しいピザの追加.
	 * 
	 * @param item
	 */
	public void insertPizza(Item item) {
		repository.insertPizza(item);
	}
	
	/**
	 * 商品一覧から見えなくする.
	 * 
	 * @param id
	 */
	public void invisible(Integer id) {
		repository.invisible(id);
	}
	
	/**
	 * 商品一覧に表示させる.
	 * 
	 * @param id
	 */
	public void visible(Integer id) {
		repository.visible(id);
	}

}
