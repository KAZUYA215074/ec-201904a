package com.example.ecommerce_a.service;

import java.util.ArrayList;
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
			order.setTotalPrice(order.getTotalPrice()+serchOrder.getTotalPrice());
			orderRepository.update(order);
		}
		// 
		for(OrderItem orderItem:order.getOrderItemList()) {			
			orderItem.setOrderId(order.getId());
			addOrderItemToCart(orderItem);
		}
		return order;
	}
	
	
	/**
	 * 注文商品と注文トッピング
	 * 
	 * @param orderItem 追加する注文商品
	 */
	public void addOrderItemToCart(OrderItem orderItem) {
		orderItem = orderItemRepository.insertItem(orderItem);
		List<OrderTopping> orderToppingList = orderItem.getOrderToppingList();
		
		for(OrderTopping orderTopping: orderToppingList) {
			orderTopping.setOrderItemId(orderItem.getId());
//			orderToppingRepository.insertOrderTopping(orderTopping);
		}
		if(orderToppingList.size() != 0) {
			orderToppingRepository.insertOrderTopping(orderToppingList);
		}
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
		List<Integer> statusList = new ArrayList<>();
		statusList.add(Order.Status.BEFORE_ORDER.getCode());
		
		List<Order> orderList = orderRepository.findByJoinedOrderByUserIdAndStatus(userId,statusList);
		if(orderList.size()!=0) {
			Order order = orderList.get(0);
			return order;
			
		} else {
			return null;
		}
	}

	/**
	 * 主キーを使って1件の注文商品情報と注文トッピング情報を削除する.
	 * 
	 * @param orderItemId 削除するID
	 */
	public void deleteByOrderItem(Integer orderItemId,Integer totalPrice,Integer orderId) {
		Order order = orderRepository.load(orderId);
		order.setTotalPrice(order.getTotalPrice()-totalPrice);
		orderRepository.update(order);
		orderItemRepository.deleteById(orderItemId);
	}
	
	/**
	 * 購入履歴を検索する.
	 * 
	 * @param userId 検索するユーザー
	 * @return 購入履歴一覧 or null
	 */
	public List<Order> showShoppingHistory(int userId) {
		List<Integer> statusList = new ArrayList<>();
		statusList.add(Order.Status.NOT_PAYMENT.getCode());
		statusList.add(Order.Status.DONE_PAYMENT.getCode());
		statusList.add(Order.Status.DONE_DELIVELY.getCode());
		List<Order> orderList = orderRepository.findByJoinedOrderByUserIdAndStatus(userId,statusList);
		if(orderList.size()!=0) {
			return orderList;
		}else {
			return null;
		}
	}
	
	
	/**
	 * すべての注文状況を検索する.
	 * 
	 * @return 注文状況一覧
	 */
	public List<Order> showOrderByStatus() {
		List<Integer> statusList = new ArrayList<>();
		statusList.add(Order.Status.NOT_PAYMENT.getCode());
		statusList.add(Order.Status.DONE_PAYMENT.getCode());
		statusList.add(Order.Status.DONE_DELIVELY.getCode());
		List<Order> orderList = orderRepository.findByJoinedOrderByStatus(statusList);
		if(orderList.size()!=0) {
			return orderList;
		}else {
			return null;
		}
	}
	
	
	/**
	 * 注文商品情報を検索する.
	 * 
	 * @param orderItemId : 注文商品ID
	 * @return 注文詳細情報
	 */
	public OrderItem showOrderItem(int orderItemId) {
		return orderItemRepository.findById(orderItemId);
	}
	
}
