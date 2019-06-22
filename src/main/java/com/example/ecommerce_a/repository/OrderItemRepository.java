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
	
	//TODO:カラム名
	/** 注文商品,注文トッピング,商品,トッピングの結合テーブルの全カラム名 */
	private static final String ALL_COLUMN_JOIN
	= "";
	//TODO:未実装
	private static final ResultSetExtractor<List<OrderItem>> ORDER_ITEM_RESULT_SET
	= (rs) -> {
		List<OrderItem> orderItemList = new ArrayList<>();
		
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
	public void deleteById(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" WITH deleted AS (DELETE FROM order_items WHERE id=:id RETURNING id)");
		sql.append(" DELETE FROM order_toppings WHERE order_item_id IN (SELECT id FROM deleted)");
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql.toString(), param);
	}
	
	//TODO;未実装
	/**
	 * 注文商品をテーブル結合して注文商品IDで検索する.
	 * 
	 * @param id 検索する注文商品ID
	 * @return 結合された注文商品
	 */
	public OrderItem findById(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT "); sql.append(ALL_COLUMN_JOIN);
		sql.append(" FROM ");	sql.append(TABLE_NAME);	sql.append(" AS oi ");
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<OrderItem> orderItemList = template.query(sql.toString(), param,ORDER_ITEM_RESULT_SET);
		if(orderItemList.size()!=0) {
			return orderItemList.get(0);
		}else {
			return null;
		}
	}
}
