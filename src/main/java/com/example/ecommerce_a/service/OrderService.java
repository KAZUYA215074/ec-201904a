package com.example.ecommerce_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.Topping;
import com.example.ecommerce_a.repository.OrderItemRepository;
import com.example.ecommerce_a.repository.OrderRepository;
import com.example.ecommerce_a.repository.OrderToppingRepository;

/**
 * 注文のサービス.
 * 
 * @author taka
 *
 */
@Service
@Transactional
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderToppingRepository orderToppingRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;

	//TODO:注文情報の挿入未実装
	/**
	 * 注文情報を挿入
	 * 
	 * @param order
	 */
	public void insert(Order order) {

	}

	/**
	 * 注文情報を更新.
	 * 
	 * @param order 更新した注文情報
	 */
	public void update(Order order) {
		orderRepository.update(order);
	}

	/**
	 * 注文情報の主キー検索.
	 * 
	 * @param OrderId
	 * @return 検索された注文情報
	 */
	public Order load(int OrderId) {
		return orderRepository.load(OrderId);
	}

	//TODO:注文情報を結合して検索未実装
	/**
	 * 注文情報を結合して検索.
	 * 
	 * @param OrderId : 注文情報のID
	 * @return 結合されて検索された注文情報
	 */
	public Order findByJoinedOrder(int OrderId) {
		return null;
	}


	/**
	 * 注文されたトッピングを追加する.
	 * 
	 * @param topping トッピング
	 */
	public void insertTopping(Topping topping) {
		orderToppingRepository.insertTopping(topping);
	}

	/**
	 * 注文された商品情報を追加する.
	 * 
	 * @param item 商品
	 */
	public void insertItem(Item item) {
		orderItemRepository.insertItem(item);
	}

	/**
	 * 主キーを使って1件の注文された商品情報を削除する.
	 * 
	 * @param id ID
	 */
	public void deleteById(Integer id) {
		orderItemRepository.deleteById(id);
	}

}
