package com.example.ecommerce_a.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Item;

@Repository
public class ItemRepository {
	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs,i)->{
		Item item = new Item();
		item.setId(rs.getInt("i_id"));
		item.setName(rs.getString("i_name"));
		item.setDescription(rs.getString("description"));
		item.setImagePath(rs.getString("imagePath"));
		item.setPriceM(rs.getInt("priceM"));
		item.setPriceL(rs.getInt("priceL"));
		return item;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	public List<Item> findAll(){
		String sql = "select id,name,description,price_m,price_l,image_path,deleted from items";
		List<Item> itemList = template.query(sql,ITEM_ROW_MAPPER);
		return itemList;
	}
	
	public List<Item> findByName(String name){
		String sql = "select id,name,description,price_m,price_l,image_path,deleted from items where name like :name";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%"+name+"%");
		List<Item> itemList = template.query(sql,param,ITEM_ROW_MAPPER);
		return itemList;
	}
	
	public Item load(Integer id) {
		String sql = "select id,name,description,price_m,price_l,image_path,deleted from items where id= :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id",id);
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
		return item;
	}
}
