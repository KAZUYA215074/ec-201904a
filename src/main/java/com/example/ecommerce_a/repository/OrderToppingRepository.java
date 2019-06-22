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
}
