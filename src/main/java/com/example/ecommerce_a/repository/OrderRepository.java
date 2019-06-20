package com.example.ecommerce_a.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.example.ecommerce_a.domain.Order;

/**
 * 注文(カート)のリポジトリ.
 * 
 * @author taka
 *
 */
public class OrderRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/** 注文情報のテーブル名前 */
	private static final String TABLE_NAME_ORDER = "orders";
	/** 注文情報のすべてのカラム名 */
	private static final String ALL_COLUMN_ORDER
	= "id,user_id,status,total_price,order_date,destination_name,destination_email,destination_zipcode,destination_address,destination_tel,delivery_time,payment_method";
	
	
	/** 注文情報RowMapper */
	private static final RowMapper<Order>
	ORDER_ROW_MAPPER = (rs,i) -> {
		Order order = new Order();
		return order;
	};
	
	
	
	
	/**
	 * 注文情報を追加する.
	 * 
	 * @param order 追加する注文情報
	 * @return 追加された注文情報
	 */
	public Order insert(Order order) {
		return null;
	}
	
	
	
	
	
	
	/**
	 * 注文情報を更新する.
	 * 
	 * @param order 更新する注文情報
	 */
	public void update(Order order) {
		
	}
	
	
	
	
	
	
	/**
	 * 注文情報の主キー検索.
	 * 
	 * @param OrderId : 注文情報のID
	 * @return 検索された注文情報
	 */
	public Order load(int OrderId) {
		return null;
	}
	
	
	
	
	
	
	
	/**
	 * 注文情報を結合して検索.
	 * 
	 * @param OrderId : 注文情報のID
	 * @return 結合されて検索された注文情報
	 */
	public Order findByJoinedOrder(int OrderId) {
		return null;
	}

}
