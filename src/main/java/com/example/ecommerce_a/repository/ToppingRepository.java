package com.example.ecommerce_a.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Topping;

//XXX:作者不明,Javadoc
/**
 * トッピングのレポジトリ.
 * 
 * @author ?
 *
 */
@Repository
public class ToppingRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/** トッピングのテーブル名前 */
	public static final String TABLE_NAME = "toppings";
	
	/** トッピングのRowMapper */
	private static final RowMapper<Topping> TOPPING_ROW_MAPPER = (rs, i) -> {
		Topping topping = new Topping();
		topping.setId(rs.getInt("id"));
		topping.setName(rs.getString("name"));
		topping.setPriceM(rs.getInt("price_m"));
		topping.setPriceL(rs.getInt("price_l"));
		return topping;
	};

	
	/**
	 * 全トッピング情報を検索する.
	 * 
	 * @return 全トッピング情報一覧
	 */
	public List<Topping> findAll() {
		String sql = "select id, name, price_m, price_l from toppings";
		List<Topping> toppingList = template.query(sql, TOPPING_ROW_MAPPER);
		return toppingList;
	}

	/**
	 * トッピング情報を主キー検索する.
	 * 
	 * @param id 検索するトッピングID
	 * @return 検索されたトッピング情報
	 */
	public Topping load(Integer id) {
		String sql = "select id, name, price_m, price_l from toppings where id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		return template.queryForObject(sql, param, TOPPING_ROW_MAPPER);
	}
}
