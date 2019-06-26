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
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName(TABLE_NAME);
		insert = withTableName.usingGeneratedKeyColumns("id");
	}

	/** 注文情報のテーブル名前 */
	private static final String TABLE_NAME = "orders";
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
		
		Integer beforeOrderId = null;
		Integer beforeOrderItemId = null;
		
		while(rs.next()) {
			// オーダー情報を取得
			Integer orderId = rs.getInt("order_id");
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
			Integer orderItemId = rs.getInt("oi_id");
			if( orderItemId != beforeOrderItemId ) {
				int itemId = rs.getInt("oi_item_id");
				
				toppingList = null;
				Item item = new Item(itemId,rs.getString("i_name"),rs.getString("i_description"),rs.getInt("i_price_m"),rs.getInt("i_price_l"),rs.getString("i_image_path"),rs.getBoolean("i_deleted"),toppingList);
				
				orderToppingList = new ArrayList<>();
				String strSize = rs.getString("oi_size");
				char size;
				if(strSize!=null) {
					size = strSize.charAt(0);
				}else {
					size = 0;
				}
				orderItem = new OrderItem(orderItemId,itemId,orderId,rs.getInt("oi_quantity"),size,item,orderToppingList);
				orderItemList.add(orderItem);
			}
			
			// トッピングリストを取得
			Integer toppingId = rs.getInt("ot_topping_id");
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
		String sql = "UPDATE " + TABLE_NAME + " SET id=:id,user_id=:userId,status=:status,total_price=:totalPrice,"
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
		String sql = "SELECT " + ALL_COLUMN_ORDER + " FROM "+ TABLE_NAME +" WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", orderId);
		Order order = template.queryForObject(sql, param, ORDER_ROW_MAPPER);
		System.out.println(order);
		return order;
	}

	/**
	 * 注文情報一覧を結合して検索.
	 
	 * @param userIds : 検索するユーザーIDの配列
	 * @param statuses : 検索する注文状況の配列
	 * @return 結合されて検索された注文情報一覧
	 */
	public List<Order> findByJoinedOrderByUserIdAndStatus(int userId,List<Integer> statusList) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");		sql.append(ALL_COLUMN_JOIN);
		sql.append(" FROM ");		sql.append(TABLE_NAME);			sql.append(" AS o ");
		sql.append(" INNER JOIN ");	sql.append(OrderItemRepository.TABLE_NAME);		sql.append(" AS oi ");
		sql.append(" ON o.id = oi.order_id ");
		sql.append(" FULL OUTER JOIN ");	sql.append(OrderToppingRepository.TABLE_NAME);	sql.append(" AS ot ");
		sql.append(" ON oi.id = ot.order_item_id ");
		sql.append(" FULL OUTER JOIN ");	sql.append(ItemRepository.TABLE_NAME);			sql.append(" AS i ");
		sql.append(" ON oi.item_id = i.id ");
		sql.append(" FULL OUTER JOIN ");	sql.append(ToppingRepository.TABLE_NAME);		sql.append(" AS t ");
		sql.append(" ON ot.topping_id = t.id ");
		sql.append(" WHERE (o.user_id,o.status) IN( ");
		MapSqlParameterSource mapParam = new MapSqlParameterSource();
		int times = statusList.size() ;
		for(int i=0;i<times;i++) {
			sql.append("(:userId");
			sql.append(i);
			sql.append(",:status");
			sql.append(i);
			sql.append("),");
			mapParam.addValue("userId"+i, userId);
			mapParam.addValue("status"+i, statusList.get(i));
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(" ) ORDER BY o.id" );
		return template.query(sql.toString(), mapParam,ORDER_RESULT_SET);
	}
	
	
	public List<Order> findByJoinedOrderByStatus(List<Integer> statusList,String sort) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");		sql.append(ALL_COLUMN_JOIN);
		sql.append(" FROM ");		sql.append(TABLE_NAME);			sql.append(" AS o ");
		sql.append(" INNER JOIN ");	sql.append(OrderItemRepository.TABLE_NAME);		sql.append(" AS oi ");
		sql.append(" ON o.id = oi.order_id ");
		sql.append(" FULL OUTER JOIN ");	sql.append(OrderToppingRepository.TABLE_NAME);	sql.append(" AS ot ");
		sql.append(" ON oi.id = ot.order_item_id ");
		sql.append(" FULL OUTER JOIN ");	sql.append(ItemRepository.TABLE_NAME);			sql.append(" AS i ");
		sql.append(" ON oi.item_id = i.id ");
		sql.append(" FULL OUTER JOIN ");	sql.append(ToppingRepository.TABLE_NAME);		sql.append(" AS t ");
		sql.append(" ON ot.topping_id = t.id ");
		sql.append(" WHERE (o.status) IN( ");
		MapSqlParameterSource mapParam = new MapSqlParameterSource();
		for(int i=0;i<statusList.size();i++) {
			sql.append("(:status");
			sql.append(i);
			sql.append("),");
			mapParam.addValue("status"+i, statusList.get(i));
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(" ) ORDER BY ");
		if(sort==null) {
			sql.append("o.id");
		}else {
			switch(sort) {
			case "orderDate":
				sql.append("o.order_date");
				break;
			case "deliveryTime":
				sql.append("o.delivery_time");
				break;
			default:
				sql.append("o.id");
				break;
			}
		}
		return template.query(sql.toString(), mapParam,ORDER_RESULT_SET);
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
		sql.append(TABLE_NAME);	sql.append(" WHERE user_id=:userId AND status=:status");
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		List<Order> orderList = template.query(sql.toString(), param, ORDER_ROW_MAPPER);
		if(orderList.size()!=0) {
			return orderList.get(0);
		}else {
			return null;
		}
	}

}
