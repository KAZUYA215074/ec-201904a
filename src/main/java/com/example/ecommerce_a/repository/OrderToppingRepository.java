package com.example.ecommerce_a.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Topping;

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

	/**
	 * 注文されたトッピングを追加する.
	 * 
	 * @param topping トッピング
	 */
	public void insertTopping(Topping topping) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(topping);
		String sql = "INSERT INTO order_toppings(id,topping_id,order_id)values(:id,:toppingId,:orderId);";
		template.update(sql, param);
	}
}
