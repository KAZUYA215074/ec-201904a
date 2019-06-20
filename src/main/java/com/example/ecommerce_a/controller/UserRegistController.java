package com.example.ecommerce_a.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.form.InsertUserForm;
import com.example.ecommerce_a.service.UserService;

/**
 * ユーザー情報を操作する.
 * 
 * @author yuki.maekawa
 *
 */
@Controller
@RequestMapping("/regist")
public class UserRegistController {
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public InsertUserForm setUpUserForm() {
		return new InsertUserForm();
	}
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * ユーザー登録画面を出力.
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String toRegist(Model model) {
		return "register_user";
	}
	
	/**
	 * ユーザー情報を登録.
	 * 
	 * @param ユーザー情報用フォーム
	 * @param リクエストパラメータ
	 * @return 商品一覧画面
	 */
	@RequestMapping("/regist")
	public String regist(@Validated InsertUserForm form, BindingResult result, Model model, String checkedpassword) {
		//メールアドレスのダブりがないかチェック
		Boolean hasMailAddress = userService.isCheckByMailAddress(form.getMailAddress());
		if(hasMailAddress) {
			result.rejectValue("mailAddress", null, "すでに使われているメールアドレスです");
		}
		//エラーチェック
		if(result.hasErrors()) {
			return toRegist(model);
		}
		User user = new User();
		BeanUtils.copyProperties(form, user);
		//確認用パスワードチェック
		if(!user.getPassword().equals(checkedpassword)) {
			result.reject("password", null, "パスワードが一致しません");
			return toRegist(model);
		}
		//パスワードハッシュ化
		String hash = encoder.encode(user.getPassword());
		user.setPassword(hash);
		
		userService.insert(user);
		
		return "forward:/item/showList";
	}

}
