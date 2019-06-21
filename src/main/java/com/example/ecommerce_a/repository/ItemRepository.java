package com.example.ecommerce_a.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Item;

/**
 * ピザを操作するリポジトリ.
 * 
 * @author Makoto
 *
 */
/**
 * @author Makoto
 */
@Repository
public class ItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	/** ItemのRowMapper */
	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString("description"));
		item.setPriceM(rs.getInt("price_m"));
		item.setPriceL(rs.getInt("price_l"));
		item.setImagePath(rs.getString("image_path"));
		item.setDeleted(rs.getBoolean("deleted"));
		return item;
	};
	
	/**
	 * ピザを全検検索する.
	 * 
	 * @return ピザのリスト
	 */
	public List<Item> findAll() {
		String sql = "select id,name,description,price_m,price_l,image_path,deleted from items";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}

	/*
	 * あいまい検索を行う.
	 * 
	 * @param name 入力された名前
	 * 
	 * @param offset ページネーション
	 * 
	 * @return 任意の件数の商品一覧
	 */
	public List<Item> findByName(String name) {
		String sql = "select id,name,description,price_m,price_l,image_path,deleted from items where name like :name";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 全ピザ名を検索.
	 * 
	 * @return 全ピザ名一覧
	 */
	public List<String> itemAllName() {
		String sql = "select id,name,description,price_m,price_l,image_path,deleted from items";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		List<String> nameList = new ArrayList<>();
		for (Item item : itemList) {
			nameList.add(item.getName());
		}
		return nameList;
	}

	/**
	 * ピザの主キー検索.
	 * 
	 * @param id ピザのid
	 * @return ピザ
	 */
	public Item load(Integer id) {
		String sql = "select id, name, description, price_m, price_l, image_path, deleted from items where id= :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		return template.queryForObject(sql, param, ITEM_ROW_MAPPER);
	}

	/**
	 * 商品一覧の並び替え.
	 * 
	 * @param sort   ソートするカラム名
	 * @param offset ページネーション
	 * @return 商品一覧
	 */
	public List<Item> sort(String sort) {
		String sql = "select id,name,description,price_m,price_l,image_path,deleted from items ";
		if ("price_m".equals(sort)) {
			sql += " order by price_m asc ";
		} else if ("name".equals(sort)) {
			sql += " order by name asc ";
		}
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}
}
