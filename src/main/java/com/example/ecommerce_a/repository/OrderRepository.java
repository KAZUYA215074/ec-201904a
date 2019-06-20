package com.example.ecommerce_a.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.OrderTopping;

/**
 * 注文(カート)のリポジトリ.
 * 
 * @author taka
 *
 */
@Repository
public class OrderRepository {

//	@Autowired
//	private NamedParameterJdbcTemplate template;
//
//	/** 注文情報のテーブル名前 */
//	private static final String TABLE_NAME_ORDER = "orders";
//	/** 注文情報のすべてのカラム名 */
//	private static final String ALL_COLUMN_ORDER = "id,user_id,status,total_price,order_date,destination_name,destination_email,destination_zipcode,destination_address,destination_tel,delivery_time,payment_method";
//
//	/** 注文情報RowMapper */
//	private static final RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) -> {
//		Order order = new Order();
//		order.setId(rs.getInt("id"));
//		order.setUserId(rs.getInt("userId"));
//		order.setStatus(rs.getInt("status"));
//		order.setTotalPrice(rs.getInt("totalPrice"));
//		order.setOrderDate(rs.getDate("orderDate"));
//		order.setDestinationName(rs.getString("destinationName"));
//		order.setDestinationEmail(rs.getString("destinationEmail"));
//		order.setDestinationZipcode(rs.getString("destinationZipcode"));
//		order.setDestinationAddress(rs.getString("destinationAddress"));
//		order.setDestinationTel(rs.getString("destinationTel"));
//		order.setDeliveryTime(rs.getTimestamp("deliveryTime"));
//		order.setPaymentMethod(rs.getInt("paymentMethod"));
//		return order;
//	};
//
//
//	@Autowired
//	private NamedParameterJdbcTemplate template;
//	
//	/** 注文情報のテーブル名前 */
//	private static final String TABLE_NAME_ORDER = "orders";
//	/** 注文情報のすべてのカラム名 */
//	private static final String ALL_COLUMN_ORDER
//	= "id,user_id,status,total_price,order_date,destination_name,destination_email,destination_zipcode,destination_address,destination_tel,delivery_time,payment_method";
//	
//	/** 注文商品テーブル名 */
//	private static final String TABLE_NAME_ORDERITEM = "order_items";
//	/** 注文トッピングテーブル名 */
//	private static final String TABLE_NAME_ORDERTOPPING = "order_toppings";
//	/** 注文,注文商品,注文トッピングのすべてのカラム名 */
//	private static final String ALL_COLUMN_JOIN
//	= " o.id AS order_id, o.user_id AS order_user_id, o.status AS order_status, o.total_price AS order_total_price, o.order_date AS order_date, o.destination_name AS order_destination_name, "
//	+ " o.destination_email AS order_destination_email, o.destination_zipcode AS order_destination_zipcode, o.destination_address AS order_destination_address, "
//	+ " o.destination_tel AS order_destination_tel, o.delivery_time AS order_delivery_time, o.payment_method AS order_payment_method,"
//	+ " oi.id AS oi_id, oi.item_id AS oi_item_id,  oi.quantity AS oi_quantity, oi.size AS oi_size, "
//	+ " ot.id AS ot_id,	ot.topping_id AS ot_topping_id ";
//	
//	/** 注文情報RowMapper */
//	private static final RowMapper<Order>
//	ORDER_ROW_MAPPER = (rs,i) -> {
//		Order order = new Order();
//		return order;
//	};
//	
//	private static final ResultSetExtractor<List<Order>> ORDER_RESULT_SET
//	= (rs) -> {
//		List<Order> orderList = new ArrayList<>();
//		Order order = null;
//		OrderItem orderItem = null;
//		List<OrderItem> orderItemList = null;
//		List<OrderTopping> orderToppingList = null;
//		OrderTopping orderTopping = null;
//		int beforeOrderId = 0;
//		int beforeOrderItemId = 0;
//		while(rs.next()) {
//			int orderId = rs.getInt("order_id");
//			if( orderId != beforeOrderId ) {
//				orderItemList = new ArrayList<>();
//				order = new Order(orderId,rs.getInt("order_user_id"),rs.getInt("order_status"),rs.getInt("order_total_price"),rs.getDate("order_date"),
//						rs.getString("order_destination_name"),rs.getString("order_destination_email"),rs.getString("order_destination_zipcode"),
//						rs.getString("order_destination_address"),rs.getString("order_destination_tel"),rs.getTimestamp("order_delivery_time"),
//						rs.getInt("order_payment_method"),null,orderItemList);
//				orderList.add(order);
//			}
//			
//			int orderItemId = rs.getInt("oi_id");
//			if( orderItemId != beforeOrderItemId ) {
//				orderToppingList = new ArrayList<>();
//				orderItem = new OrderItem(orderItemId,rs.getInt("oi_item_id"),orderId,rs.getInt("oi_quantity"),rs.getString("oi_size").charAt(0),null,orderToppingList);
//				orderItemList.add(orderItem);
//			}
//			orderTopping = new OrderTopping(rs.getInt("ot_id"),rs.getInt("ot_topping_id"),orderId,null);
//			orderToppingList.add(orderTopping);
//			
//			beforeOrderId = orderId;
//			beforeOrderItemId = orderItemId;
//		}
//		return orderList;
//	};
//	
//	
//	
//	
//	/**
//	 * 注文情報を追加する.
//	 * 
//	 * @param order 追加する注文情報
//	 * @return 追加された注文情報
//	 */
//	public Order insert(Order order) {
//		return null;
//	}
//	/**
//	 * 注文情報を更新する.
//	 * 
//	 * @param order 更新する注文情報
//	 */
//	public void update(Order order) {
//		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
//		String sql = "UPDATE " + TABLE_NAME_ORDER + "SET id=:id,user_id=:userId,status=:status,total_price=:totalPrice,"
//				+ "order_date=:orderDate,destination_name=:destinationName,destination_email=:destinationEmail,"
//				+ "destination_zipcode=:destinationZipcode,destination_address=:destinationAddress,destination_tel=:destinationTel,"
//				+ "delivery_time=:deliveryTime,payment_method=:paymentMethod  WHERE id=:id";
//		template.update(sql, param);
//	}
//
//	/**
//	 * 注文情報の主キー検索.
//	 * 
//	 * @param OrderId : 注文情報のID
//	 * @return 検索された注文情報
//	 */
//	public Order load(int OrderId) {
//		String sql = "SELECT " + ALL_COLUMN_ORDER + " WHERE id = :id";
//		SqlParameterSource param = new MapSqlParameterSource().addValue("orderID", OrderId);
//		Order order = template.queryForObject(sql, param, ORDER_ROW_MAPPER);
//		return order;
//	}
//
//	/**
//	 * 注文情報を結合して検索.
//	 * 
//	 * @param OrderId : 注文情報のID
//	 * @return 結合されて検索された注文情報
//	 */
//	public Order findByJoinedOrder(int OrderId) {
//		return null;
//	}
//	
//	
//	
//	
//	
//	
//	
//	/**
//	 * 注文情報を結合して検索.
//	 * 
//	 * @param orderId : 注文情報のID
//	 * @return 結合されて検索された注文情報
//	 */
//	public List<Order> findByJoinedOrder(int orderId) {
//		StringBuffer sql = new StringBuffer();
//		sql.append(" SELECT ");	sql.append(ALL_COLUMN_JOIN);
//		sql.append(" FROM ");	sql.append(TABLE_NAME_ORDER);	sql.append(" AS o ");
//		sql.append(" INNER JOIN ");	sql.append(TABLE_NAME_ORDERITEM);	sql.append(" AS oi ");
//		sql.append(" ON o.id = oi.order_id ");
//		sql.append(" INNER JOIN ");	sql.append(TABLE_NAME_ORDERTOPPING);	sql.append(" AS ot ");
//		sql.append(" ON oi.id = ot.order_item_id WHERE o.id=:orderId ORDER BY o.id" );
//		SqlParameterSource param =  new MapSqlParameterSource().addValue("orderId", orderId);
//		return template.query(sql.toString(), param,ORDER_RESULT_SET);
//	}

}
