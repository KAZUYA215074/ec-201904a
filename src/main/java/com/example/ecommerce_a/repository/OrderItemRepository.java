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
	 * 主キーを使って1件の注文された商品情報を削除する.
	 * 
	 * @param id ID
	 */
	public void deleteById(Integer id) {
		String sql = "DELETE FROM order_item WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);

	}
}
