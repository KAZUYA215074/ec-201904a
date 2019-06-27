package com.example.ecommerce_a.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ログイン時のパスを受け渡す.
 * 
 * @author yuki
 *
 */
@Controller
@RequestMapping("/toLogin")
public class UserLoginController {

	@Autowired
	private HttpSession session;

	/**
	 * ログイン画面に遷移する.
	 * 
	 * @param model : モデル
	 * @param error : エラー
	 * @return ログイン画面
	 */
	@RequestMapping("/")
	public String toLogin(Model model, @RequestParam(required = false) String error,
			@RequestHeader(name = "Referer", required = false) String referer) {
		System.out.println("referer: " + referer);
		// System.err.println("login error "+ error);
		if (error != null) {
			System.err.println(error);
			System.err.println("login failed");
			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが不正です。");
		}
		session.setAttribute("referer", referer);
		return "login";
	}

}
