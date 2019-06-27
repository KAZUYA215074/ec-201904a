package com.example.ecommerce_a.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ecommerce_a.domain.LoginUser;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.repository.UserRepository;

/**
 * ユーザーのテーブルを操作するサービス.
 * 
 * @author yuki.maekawa
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String mailAddress) throws UsernameNotFoundException {
		if (mailAddress == null || mailAddress.isEmpty()) {
			throw new UsernameNotFoundException("not mailaddress");
		}
		User user = userRepository.findByMailAddress(mailAddress);
		if (user == null) {
			throw new UsernameNotFoundException("そのメールアドレスは登録されていません");
		}
		// admin@gmail.comの時だけadminとしてログインできる
		if (user.getMailAddress().equals("admin@gmail.com")) {
			Collection<GrantedAuthority> authorityList = new ArrayList<>();
			authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			return new LoginUser(user, authorityList);
		} else {
			Collection<GrantedAuthority> authorityList = new ArrayList<>();
			authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
			return new LoginUser(user, authorityList);
		}
	}
}
