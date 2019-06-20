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

@Repository
public class ItemRepository {
	private static final int ELEMENT_COUNT = 9;
	
	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs,i)->{
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString("description"));
		item.setImagePath(rs.getString("image_path"));
		item.setPriceM(rs.getInt("price_m"));
		item.setPriceL(rs.getInt("price_l"));
		return item;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	public List<Item> findAll(){
		String sql = "select id,name,description,price_m,price_l,image_path,deleted from items";
		List<Item> itemList = template.query(sql,ITEM_ROW_MAPPER);
		return itemList;
	}
	
	public List<Item> findByName(String name,Integer offset){
		String sql = "select id,name,description,price_m,price_l,image_path,deleted from items where name like :name limit :limit offset :offset";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("name", "%"+name+"%")
				.addValue("limit",ELEMENT_COUNT)
				.addValue("offset",offset);
		List<Item> itemList = template.query(sql,param,ITEM_ROW_MAPPER);
		return itemList;
	}
	
	public List<String> itemAllName(){
		String sql = "select id,name,description,price_m,price_l,image_path,deleted from items";
		List<Item> itemList = template.query(sql,ITEM_ROW_MAPPER);
		List<String> nameList = new ArrayList<>();
		for(Item item : itemList) {
			nameList.add(item.getName());
		}
		return nameList;
	}
	
	public Item load(Integer id) {
		String sql = "select id,name,description,price_m,price_l,image_path,deleted from items where id= :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id",id);
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
		return item;
	}
	
	public List<Item> sort(String sort,Integer offset){
		String sql = "select id,name,description,price_m,price_l,image_path,deleted from items ";
		if("price_m".equals(sort)) {
			sql += " order by price_m asc ";
		}else if("name".equals(sort)) {
			sql += " order by name asc ";
		}
		sql += " limit :limit offset :offset";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("limit",ELEMENT_COUNT)
				.addValue("offset",offset);
		List<Item> itemList = template.query(sql,param,ITEM_ROW_MAPPER);
		return itemList;
	}
}
