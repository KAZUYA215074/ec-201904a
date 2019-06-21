package com.example.ecommerce_a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.OrderTopping;
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
	public Order addItemToCart(Order order) {
		// 注文に追加する
		Order serchOrder = orderRepository.findByUserIdAndStatus(order.getUserId(), order.getStatus());
		if(serchOrder == null) {
			order = orderRepository.insert(order);
		}else {
			order.setId(serchOrder.getId());
		}
		// 
		OrderItem orderItem = order.getOrderItemList().get(0);
		orderItem.setOrderId(order.getId());
		orderItem = orderItemRepository.insertItem(orderItem);
		List<OrderTopping> orderToppingList = orderItem.getOrderToppingList();
		for(OrderTopping orderTopping: orderToppingList) {
			orderTopping.setOrderItemId(orderItem.getId());
			orderToppingRepository.insertTopping(orderTopping);
		}
		return order;
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


	/**
	 * 注文情報を結合して検索.
	 * 
	 * @param userId : ログイン中ユーザーのID
	 * @return 結合されて検索された注文情報
	 */
	public Order showShoppingCart(int userId) {
		List<Order> orderList = orderRepository.findByJoinedOrderByUserIdAndStatus(userId,0);
		System.out.println(orderList);
		if(orderList.size()!=0) {
			return orderList.get(0);
		}else {
			return null;
		}
	}


	/**
	 * 注文された商品情報を追加する.
	 * 
	 * @param item 商品
	 */
//	public void insertItem(Item item) {
//		orderItemRepository.insertItem(item);
//	}

	/**
	 * 主キーを使って1件の注文商品情報と注文トッピング情報を削除する.
	 * 
	 * @param id 削除するID
	 */
	public void deleteById(Integer id) {
		orderItemRepository.deleteById(id);
	}

}
