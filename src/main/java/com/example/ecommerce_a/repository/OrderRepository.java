package com.example.ecommerce_a.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

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
	private static final String ALL_COLUMN_ORDER = "id,user_id,status,total_price,order_date,destination_name,destination_email,destination_zipcode,destination_address,destination_tel,delivery_time,payment_method";

	/** 注文情報RowMapper */
	private static final RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) -> {
		Order order = new Order();
		order.setId(rs.getInt("id"));
		order.setUserId(rs.getInt("userId"));
		order.setStatus(rs.getInt("status"));
		order.setTotalPrice(rs.getInt("totalPrice"));
		order.setOrderDate(rs.getDate("orderDate"));
		order.setDestinationName(rs.getString("destinationName"));
		order.setDestinationEmail(rs.getString("destinationEmail"));
		order.setDestinationZipcode(rs.getString("destinationZipcode"));
		order.setDestinationAddress(rs.getString("destinationAddress"));
		order.setDestinationTel(rs.getString("destinationTel"));
		order.setDeliveryTime(rs.getTimestamp("deliveryTime"));
		order.setPaymentMethod(rs.getInt("paymentMethod"));
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
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		String sql = "UPDATE " + TABLE_NAME_ORDER + "SET id=:id,user_id=:userId,status=:status,total_price=:totalPrice,"
				+ "order_date=:orderDate,destination_name=:destinationName,destination_email=:destinationEmail,"
				+ "destination_zipcode=:destinationZipcode,destination_address=:destinationAddress,destination_tel=:destinationTel,"
				+ "delivery_time=:deliveryTime,payment_method=:paymentMethod  WHERE id=:id";
		template.update(sql, param);
	}

	/**
	 * 注文情報の主キー検索.
	 * 
	 * @param OrderId : 注文情報のID
	 * @return 検索された注文情報
	 */
	public Order load(int OrderId) {
		String sql = "SELECT " + ALL_COLUMN_ORDER + " WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderID", OrderId);
		Order order = template.queryForObject(sql, param, ORDER_ROW_MAPPER);
		return order;
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
