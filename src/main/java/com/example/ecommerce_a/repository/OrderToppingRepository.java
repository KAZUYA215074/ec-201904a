package com.example.ecommerce_a.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
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
	public void insertTopping(OrderTopping orderTopping) {
		String sql = "INSERT INTO order_toppings(topping_id, order_item_id) values(:toppingId,:orderItemId);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderTopping);
		template.update(sql, param);
	}
}
