package com.example.ecommerce_a.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.ecommerce_a.utils.ConvertUtils;

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

	@Autowired
	private PasswordEncoder encoder;

	@ModelAttribute
	public InsertUserForm setUpUserForm() {
		return new InsertUserForm();
	}

	/**
	 * ユーザー登録画面を出力する.
	 * 
	 * @return ユーザ登録画面
	 */
	@RequestMapping("/")
	public String toRegist(Model model) {
		return "register_user";
	}

	/**
	 * ユーザー情報を登録する.
	 * 
	 * @param form            ユーザー情報用フォーム
	 * @param result          エラー
	 * @param model           リクエストパラメータ
	 * @param checkedpassword 確認用パスワード
	 * @return 商品一覧画面
	 */
	@RequestMapping("/regist")
	public String regist(@Validated InsertUserForm form, BindingResult result, Model model) {
		// メールアドレスのダブりがないかチェック
		Boolean hasMailAddress = userService.isCheckByMailAddress(form.getMailAddress());
		if (hasMailAddress) {
			result.rejectValue("mailAddress", null, "すでに使われているメールアドレスです");
		}
		// 確認用パスワードチェック
		if (!form.getPassword().equals(form.getCheckedpassword())) {
			result.rejectValue("password", null, "パスワードが一致しません");
		}
		// エラーチェック
		if (result.hasErrors()) {
			return toRegist(model);
		}
		User user = new User();
		BeanUtils.copyProperties(form, user);
		user.setZipCode(ConvertUtils.getDelHyphenZipCode(form.getZipCode()));
		user.setTelephone(ConvertUtils.getHypehnTelephone(form.getTelephone()));
		// パスワードハッシュ化
		String hash = encoder.encode(user.getPassword());
		user.setPassword(hash);

		userService.insert(user);

		return "redirect:/toLogin/";
	}

}