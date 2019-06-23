package com.example.ecommerce_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.repository.UserRepository;



/**
 * ユーザー登録に関する操作を行う.
 * 
 * @author yuki.maekawa
 *
 */
@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * ユーザー情報を登録.
	 * 
	 * @param user
	 */
	public void insert(User user) {
		userRepository.insert(user);
	}
	
	/**
	 * メールアドレスからアカウントの有無を調べる.
	 * 
	 * @param mailAddress
	 * @return アカウントの有無
	 * 
	 */
	public Boolean isCheckByMailAddress(String mailAddress) {
		if(userRepository.findByMailAddress(mailAddress)!= null) {
			return true;
		}else {
			return false;
		}
	}
	
	//XXX:未テスト
	/**
	 * ユーザー情報を更新する.
	 * 
	 * @param user ユーザー情報
	 */
	public void update(User user) {
		userRepository.update(user);
	}
	
	//XXX:未テスト
	/**
	 * ユーザー情報を削除する.
	 * 
	 * @param id ユーザーID
	 */
	public void delete(Integer id) {
		userRepository.delete(id);
	}

}
