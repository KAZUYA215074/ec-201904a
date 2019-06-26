package com.example.ecommerce_a.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Item;

/**
 * 商品の変更、追加を行うリポジトリ.
 * 
 * @author yuki
 *
 */
@Repository
public class ProductManagementRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * 新商品の追加を行う.
	 * 
	 * @param 商品
	 */
	public void insertPizza(Item item) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		String sql = "insert into items values((select max(id)+1 from items), :name, :description, :priceM, :priceL, :imagePath);";
		template.update(sql, param);
	}
	
	/**
	 * 商品一覧から見えなくする.
	 * 
	 * @param id
	 */
	public void invisible(Integer id) {
		String sql ="update items set deleted=true where id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
	
	/**
	 * 商品一覧に表示する.
	 * 
	 * @param id
	 */
	public void visible(Integer id) {
		String sql ="update items set deleted=false where id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
	
	
	/**
	 * ピザを削除する.
	 * 
	 * @param id ピザのid
	 */
	public void delete(Integer id) {
		String sql = "delete from items where id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
	
}
