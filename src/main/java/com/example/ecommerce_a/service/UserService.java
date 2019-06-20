package com.example.ecommerce_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.repository.UserRepository;


//XXX:Javadoc書こう
/**
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

}
