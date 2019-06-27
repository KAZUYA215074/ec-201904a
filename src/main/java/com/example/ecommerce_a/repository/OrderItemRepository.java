package com.example.ecommerce_a.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.OrderTopping;
import com.example.ecommerce_a.domain.Topping;

/**
 * order_itemテーブルを操作するリポジトリ.
 * 
 * @author risa.nazato
 *
 */
@Repository
public class OrderItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/* 追加時に主キー取得 */
	private SimpleJdbcInsert insert;

	/** 注文商品テーブル名 */
	public static final String TABLE_NAME = "order_items";

	/** すべてのカラム名 */
	// private static final String ALL_COLUMN = "id,item_id,order_id,quantity,size";

	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("order_items");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}

	/** 注文商品,注文トッピング,商品,トッピングの結合テーブルの全カラム名 */
	private static final String ALL_COLUMN_JOIN = " oi.id AS oi_id, oi.item_id AS oi_item_id, oi.order_id AS oi_order_id, oi.quantity AS oi_quantity, oi.size AS oi_size, "
			+ " ot.id AS ot_id,	ot.topping_id AS ot_topping_id, "
			+ " i.name AS i_name, i.description AS i_description, i.price_m AS i_price_m, i.price_l AS i_price_l, i.image_path AS i_image_path, i.deleted AS i_deleted,"
			+ " t.name AS t_name, t.price_m AS t_price_m, t.price_l AS t_price_l";

	/** 注文詳細のExtractor */
	private static final ResultSetExtractor<List<OrderItem>> ORDER_ITEM_RESULT_SET = (rs) -> {
		List<OrderItem> orderItemList = new ArrayList<>();
		List<OrderTopping> orderToppingList = null;
		List<Topping> toppingList = null;
		OrderItem orderItem = null;

		Integer beforeOrderItemId = null;
		while (rs.next()) {

			// アイテムリストを取得
			Integer orderItemId = rs.getInt("oi_id");
			if (orderItemId != beforeOrderItemId) {
				int itemId = rs.getInt("oi_item_id");
				toppingList = null;
				Item item = new Item(itemId, rs.getString("i_name"), rs.getString("i_description"),
						rs.getInt("i_price_m"), rs.getInt("i_price_l"), rs.getString("i_image_path"),
						rs.getBoolean("i_deleted"), toppingList);

				orderToppingList = new ArrayList<>();
				String strSize = rs.getString("oi_size");
				char size;
				if (strSize != null) {
					size = strSize.charAt(0);
				} else {
					size = 0;
				}
				orderItem = new OrderItem(orderItemId, itemId, rs.getInt("oi_order_id"), rs.getInt("oi_quantity"), size,
						item, orderToppingList);
				orderItemList.add(orderItem);
			}

			// トッピングリストを取得
			Integer toppingId = rs.getInt("ot_topping_id");
			Topping topping = new Topping(toppingId, rs.getString("t_name"), rs.getInt("t_price_m"),
					rs.getInt("t_price_L"));
			OrderTopping orderTopping = new OrderTopping(rs.getInt("ot_id"), toppingId, orderItemId, topping);
			orderToppingList.add(orderTopping);

			beforeOrderItemId = orderItemId;
		}

		return orderItemList;
	};

	/**
	 * 注文された商品情報を追加する.
	 * 
	 * @param item 商品
	 */
	public OrderItem insertItem(OrderItem orderItem) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		Number key = insert.executeAndReturnKey(param);
		orderItem.setId(key.intValue());
		return orderItem;
	}

	/**
	 * 注文商品のIDを指定して注文商品と注文トッピングを削除する.
	 * 
	 * @param id 削除するID
	 */
	public void deleteJoinById(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" WITH deleted AS (DELETE FROM order_items WHERE id=:id RETURNING id)");
		sql.append(" DELETE FROM order_toppings WHERE order_item_id IN (SELECT id FROM deleted)");
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql.toString(), param);
	}

	/**
	 * 注文商品を削除する.
	 * 
	 * @param id 削除する注文商品ID
	 * 
	 */
	public void deleteById(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE FROM ");
		sql.append(TABLE_NAME);
		sql.append(" WHERE id = :id");
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql.toString(), param);
	}

	/**
	 * 注文商品をテーブル結合して注文商品IDで検索する.
	 * 
	 * @param id 検索する注文商品ID
	 * @return 結合された注文商品
	 */
	public OrderItem findById(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append(ALL_COLUMN_JOIN);
		sql.append(" FROM ");
		sql.append(TABLE_NAME);
		sql.append(" AS oi ");
		sql.append(" FULL OUTER JOIN ");
		sql.append(OrderToppingRepository.TABLE_NAME);
		sql.append(" AS ot ");
		sql.append(" ON oi.id = ot.order_item_id ");
		sql.append(" FULL OUTER JOIN ");
		sql.append(ItemRepository.TABLE_NAME);
		sql.append(" AS i ");
		sql.append(" ON oi.item_id = i.id ");
		sql.append(" FULL OUTER JOIN ");
		sql.append(ToppingRepository.TABLE_NAME);
		sql.append(" AS t ");
		sql.append(" ON ot.topping_id = t.id ");
		sql.append(" WHERE oi.id = :id ");

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<OrderItem> orderItemList = template.query(sql.toString(), param, ORDER_ITEM_RESULT_SET);
		if (orderItemList.size() != 0) {
			return orderItemList.get(0);
		} else {
			return null;
		}
	}

	// XXX:未テスト
	/**
	 * 注文商品を更新する.
	 * 
	 * @param orderItem 更新する注文商品
	 */
	public void update(OrderItem orderItem) {
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE ");
		sql.append(TABLE_NAME);
		sql.append(" SET item_id=:itemId,order_id=:orderId,quantity=:quantity,size=:size WHERE id=:id ");
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		template.update(sql.toString(), param);
	}
}
