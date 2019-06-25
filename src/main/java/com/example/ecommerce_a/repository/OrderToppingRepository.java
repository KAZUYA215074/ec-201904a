package com.example.ecommerce_a.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.OrderTopping;

/**
 * order_toppingテーブルを操作するリポジトリ.
 * 
 * @author risa.nazato
 *
 */
@Repository
public class OrderToppingRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/** 注文トッピングのテーブル名 */
	public static final String TABLE_NAME = "order_toppings";

	/**
	 * 注文されたトッピングを追加する.
	 * 
	 * @param topping トッピング
	 */
	public void insertOrderTopping(OrderTopping orderTopping) {
		String sql = "INSERT INTO order_toppings(topping_id, order_item_id) values(:toppingId,:orderItemId);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderTopping);
		template.update(sql, param);
	}
	
	
	/**
	 * 注文されたトッピングをまとめて追加する.
	 * 
	 * @param orderToppingList 注文トッピングのリスト
	 */
	public void insertOrderTopping(List<OrderTopping> orderToppingList) {
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO ");	sql.append(TABLE_NAME);
		sql.append("(topping_id,order_item_id) VALUES");
		MapSqlParameterSource mapParam = new MapSqlParameterSource();
		int times = orderToppingList.size();
		for(int i=0;i<times;i++) {
			sql.append("(:toppingId"+i);
			sql.append(",:orderItemId"+i);
			sql.append("),");
			mapParam.addValue("toppingId"+i, orderToppingList.get(i).getToppingId());
			mapParam.addValue("orderItemId"+i, orderToppingList.get(i).getOrderItemId());
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		template.update(sql.toString(), mapParam);
	}
	
	/**
	 * IDを指定して注文トッピング情報を削除する.
	 * 
	 * @param Id 削除するID
	 */
	public void deleteById(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE FROM ");	sql.append(TABLE_NAME);
		sql.append(" WHERE id=:id ");
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql.toString(), param);
	}
	
	/**
	 * 注文商品IDを指定して注文トッピングを削除する.
	 * 
	 * @param orderItemId 削除する注文商品ID
	 */
	public void deleteByOrderItemId(Integer orderItemId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE FROM ");	sql.append(TABLE_NAME);
		sql.append(" WHERE order_item_id=:orderItemId ");
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderItemId", orderItemId);
		template.update(sql.toString(), param);
	}
}
