package com.example.ecommerce_a.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.OrderItem;

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
	private static final String ALL_COLUMN = "id,item_id,order_id,quantity,size";
	
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate)template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("order_items");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}

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
	public void deleteById(Integer id) {
		StringBuffer sql = new StringBuffer();
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		sql.append(" WITH deleted AS (DELETE FROM order_items WHERE id=:id RETURNING id)");
		sql.append(" DELETE FROM order_toppings WHERE order_item_id IN (SELECT id FROM deleted)");
		template.update(sql.toString(), param);

	}
}
