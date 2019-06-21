package com.example.ecommerce_a.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Item;

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

	/**
	 * 注文された商品情報を追加する.
	 * 
	 * @param item 商品
	 */
	public void insertItem(Item item) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		String sql = "INSERT INTO order_item(id,item_id,order_id,quantity,size)values(:id,:item_id,:order_id,:quantity,:size);";
		template.update(sql, param);
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
