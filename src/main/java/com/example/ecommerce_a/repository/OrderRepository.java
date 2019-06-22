package com.example.ecommerce_a.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.OrderTopping;
import com.example.ecommerce_a.domain.Topping;
import com.example.ecommerce_a.domain.User;

/**
 * 注文(カート)のリポジトリ.
 * 
 * @author taka
 *
 */
@Repository
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/* 追加時に主キー取得 */
	private SimpleJdbcInsert insert;
	
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate)template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName(TABLE_NAME_ORDER);
		insert = withTableName.usingGeneratedKeyColumns("id");
	}

	/** 注文情報のテーブル名前 */
	private static final String TABLE_NAME_ORDER = "orders";
	/** 注文情報のすべてのカラム名 */
	private static final String ALL_COLUMN_ORDER = "id,user_id,status,total_price,order_date,destination_name,destination_email,destination_zipcode,destination_address,destination_tel,delivery_time,payment_method";

	/** 注文情報RowMapper */
	private static final RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) -> {
		Order order = new Order();
		order.setId(rs.getInt("id"));
		order.setUserId(rs.getInt("user_id"));
		order.setStatus(rs.getInt("status"));
		order.setTotalPrice(rs.getInt("total_price"));
		order.setOrderDate(rs.getDate("order_date"));
		order.setDestinationName(rs.getString("destination_name"));
		order.setDestinationEmail(rs.getString("destination_email"));
		order.setDestinationZipcode(rs.getString("destination_zipcode"));
		order.setDestinationAddress(rs.getString("destination_address"));
		order.setDestinationTel(rs.getString("destination_tel"));
		order.setDeliveryTime(rs.getTimestamp("delivery_time"));
		order.setPaymentMethod(rs.getInt("payment_method"));
		return order;
	};

	
	/** 注文商品テーブル名 */
	private static final String TABLE_NAME_ORDERITEM = "order_items";
	/** 注文トッピングテーブル名 */
	private static final String TABLE_NAME_ORDERTOPPING = "order_toppings";
	/** アイテムテーブル名 */
	private static final String TABLE_NAME_ITEM = "items";
	/** トッピングテーブル名 */
	private static final String TABLE_NAME_TOPPING = "toppings";
	/** 注文,注文商品,注文トッピングのすべてのカラム名 */
	private static final String ALL_COLUMN_JOIN
	= " o.id AS order_id, o.user_id AS order_user_id, o.status AS order_status, o.total_price AS order_total_price, o.order_date AS order_date, o.destination_name AS order_destination_name, "
	+ " o.destination_email AS order_destination_email, o.destination_zipcode AS order_destination_zipcode, o.destination_address AS order_destination_address, "
	+ " o.destination_tel AS order_destination_tel, o.delivery_time AS order_delivery_time, o.payment_method AS order_payment_method,"
	+ " oi.id AS oi_id, oi.item_id AS oi_item_id,  oi.quantity AS oi_quantity, oi.size AS oi_size, "
	+ " ot.id AS ot_id,	ot.topping_id AS ot_topping_id, "
	+ " i.name AS i_name, i.description AS i_description, i.price_m AS i_price_m, i.price_l AS i_price_l, i.image_path AS i_image_path, i.deleted AS i_deleted,"
	+ " t.name AS t_name, t.price_m AS t_price_m, t.price_l AS t_price_l";
	
	/** 注文情報のExtractor */
	private static final ResultSetExtractor<List<Order>> ORDER_RESULT_SET
	= (rs) -> {
		List<Order> orderList = new ArrayList<>();
		List<OrderItem> orderItemList = null;
		List<OrderTopping> orderToppingList = null;
		List<Topping> toppingList = null;
		OrderItem orderItem = null;
		
		int beforeOrderId = 0;
		int beforeOrderItemId = 0;
		
		while(rs.next()) {
			
			// オーダー情報を取得
			int orderId = rs.getInt("order_id");
			if( orderId != beforeOrderId ) {
				orderItemList = new ArrayList<>();
				User user = new User();
				Order order = new Order(orderId,rs.getInt("order_user_id"),rs.getInt("order_status"),rs.getInt("order_total_price"),rs.getDate("order_date"),
						rs.getString("order_destination_name"),rs.getString("order_destination_email"),rs.getString("order_destination_zipcode"),
						rs.getString("order_destination_address"),rs.getString("order_destination_tel"),rs.getTimestamp("order_delivery_time"),
						rs.getInt("order_payment_method"),user,orderItemList);
				orderList.add(order);
			}
			
			// アイテムリストを取得
			int orderItemId = rs.getInt("oi_id");
			if( orderItemId != beforeOrderItemId ) {
				int itemId = rs.getInt("oi_item_id");
				
				toppingList = null;
				Item item = new Item(itemId,rs.getString("i_name"),rs.getString("i_description"),rs.getInt("i_price_m"),rs.getInt("i_price_l"),rs.getString("i_image_path"),rs.getBoolean("i_deleted"),toppingList);
				
				orderToppingList = new ArrayList<>();
				orderItem = new OrderItem(orderItemId,itemId,orderId,rs.getInt("oi_quantity"),rs.getString("oi_size").charAt(0),item,orderToppingList);
				orderItemList.add(orderItem);
			}
			
			// トッピングリストを取得
			int toppingId = rs.getInt("ot_topping_id");
			Topping topping = new Topping(toppingId,rs.getString("t_name"),rs.getInt("t_price_m"),rs.getInt("t_price_L"));
			OrderTopping orderTopping = new OrderTopping(rs.getInt("ot_id"),toppingId,orderId,topping);
			orderToppingList.add(orderTopping);
			
			beforeOrderId = orderId;
			beforeOrderItemId = orderItemId;
		}
		return orderList;
	};
	
	
	/**
	 * 注文情報を追加する.
	 * 
	 * @param order 追加する注文情報
	 * @return 追加された注文情報
	 */
	public Order insert(Order order) {
//		String sql = "insert into orders(user_id, status, total_price, "
//				+ "order_date, destination_name, destination_email, destination_zipcode, "
//				+ "destination_address, destination_tel, delivery_time, payment_method) "
//				+ "values(:userId, :status. :totalPrice, :orderDate, "
//				+ ":destinationName, :destinationEmail, :destinationZipcode, :destinationAddress, "
//				+ ":destinationTel, :deliveryTime, :paymentMethod)";
		
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		Number key = insert.executeAndReturnKey(param);
		order.setId(key.intValue());
		return order;
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
	 * @param orderId : 注文情報のID
	 * @return 検索された注文情報
	 */
	public Order load(int orderId) {
		System.out.println(orderId);
		String sql = "SELECT " + ALL_COLUMN_ORDER + " FROM "+ TABLE_NAME_ORDER +" WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", orderId);
		Order order = template.queryForObject(sql, param, ORDER_ROW_MAPPER);
		System.out.println(order);
		return order;
	}

	/**
	 * 注文情報を結合して検索.
	 * 
	 * @param orderId : 注文情報のID
	 * @return 結合されて検索された注文情報 or null
	 */
	public List<Order> findByJoinedOrderByUserIdAndStatus(int userId,int status) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");		sql.append(ALL_COLUMN_JOIN);
		sql.append(" FROM ");		sql.append(TABLE_NAME_ORDER);			sql.append(" AS o ");
		sql.append(" INNER JOIN ");	sql.append(TABLE_NAME_ORDERITEM);		sql.append(" AS oi ");
		sql.append(" ON o.id = oi.order_id ");
		sql.append(" INNER JOIN ");	sql.append(TABLE_NAME_ORDERTOPPING);	sql.append(" AS ot ");
		sql.append(" ON oi.id = ot.order_item_id ");
		sql.append(" INNER JOIN ");	sql.append(TABLE_NAME_ITEM);			sql.append(" AS i ");
		sql.append(" ON oi.item_id = i.id ");
		sql.append(" INNER JOIN ");	sql.append(TABLE_NAME_TOPPING);			sql.append(" AS t ");
		sql.append(" ON ot.topping_id = t.id ");
		sql.append(" WHERE o.user_id=:userId AND o.status=:status ORDER BY o.id" );
		
		SqlParameterSource param =  new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		
		return template.query(sql.toString(), param,ORDER_RESULT_SET);
	}
	
	
	
	/**
	 * 注文状態をユーザーIDと注文状態を検索する.
	 * 
	 * @param userId ユーザーID
	 * @param status 注文状態
	 * @return 注文情報
	 */
	public Order findByUserIdAndStatus(int userId,int status) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");	sql.append(ALL_COLUMN_ORDER); sql.append(" FROM ");
		sql.append(TABLE_NAME_ORDER);	sql.append(" WHERE user_id=:userId AND status=:status");
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		List<Order> orderList = template.query(sql.toString(), param, ORDER_ROW_MAPPER);
		if(orderList.size()!=0) {
			return orderList.get(0);
		}else {
			return null;
		}
	}

}
