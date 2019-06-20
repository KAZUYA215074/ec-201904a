package com.example.ecommerce_a.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.User;

/**
 * Userテーブルを操作するリポジトリ.
 * 
 * @author yuki
 *
 */
@Repository
public class UserRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<User> USER_ROWMAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setMailAddress(rs.getString("mailAddress"));
		user.setPassword(rs.getString("password"));
		user.setZipCode(rs.getString("zipCode"));
		user.setAddress(rs.getString("address"));
		user.setTelephone(rs.getString("telephone"));		
		return user;
	};
	
	/**
	 * ユーザ情報を挿入します.
	 * 
	 * @param ユーザ情報
	 */
	public void insert(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "insert into users(name, email, password, zipcode, address, telephone)\n" + 
							" values(:name, :mailAddress, :password, :zipCode, :address, :telephone);";
		template.update(sql, param);
	}
	
	/**
	 * メールアドレスからユーザー情報を取得.
	 * 
	 * @param メールアドレス
	 * @return ユーザー情報　存在しない場合はnullを返す
	 */
	public User findByMailAddress(String mailAddress) {
		String sql = "select name, password, zipcode, address, telephone from users where email=:mailAddress";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress);
		List<User> userList = template.query(sql, param, USER_ROWMAPPER);
		if(userList.size()==0) {
			return null;
		}
		return userList.get(0);
	}

}
