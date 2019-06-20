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
	
	public List<Item> sort(String sort){
		String sql = "\"select id,name,description,price_m,price_l,image_path,deleted from items order by ";
		if("price_m".equals(sort)) {
			sql +=  "price_m asc";
		}else if("name".equals(sort)) {
			sql += "name asc";
		}
		List<Item> itemList = template.query(sql,ITEM_ROW_MAPPER);
		return itemList;
	}
}
