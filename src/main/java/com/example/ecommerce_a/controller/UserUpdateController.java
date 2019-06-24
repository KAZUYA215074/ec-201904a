package com.example.ecommerce_a.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.LoginUser;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.form.UpdateUserForm;
import com.example.ecommerce_a.service.UserService;

@Controller
@RequestMapping("/user")
public class UserUpdateController {
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public UpdateUserForm setUpUserForm() {
		return new UpdateUserForm();
	}
	
	@RequestMapping("/info")
	public String info(Model model, String name, @AuthenticationPrincipal LoginUser loginUser) {
		User user = new User();
		user.setName(loginUser.getUser().getName());
		user.setMailAddress(loginUser.getUser().getMailAddress());
		user.setZipCode(loginUser.getUser().getZipCode());
		user.setAddress(loginUser.getUser().getAddress());
		user.setTelephone(loginUser.getUser().getTelephone());
		model.addAttribute("user",user);
		return "user_info";
	}
	
	@RequestMapping("/edit")
	public String edit(UpdateUserForm form,Model model, @AuthenticationPrincipal LoginUser loginUser) {
		BeanUtils.copyProperties(loginUser.getUser(), form);
		return "user_edit";
	}
	
	@RequestMapping("/update")
	public String update(@Validated UpdateUserForm form,BindingResult result,Model model,@AuthenticationPrincipal LoginUser loginUser) {
		if(result.hasErrors()) {
			System.out.println(result.getAllErrors().get(0).getDefaultMessage());
			return edit(form,model,loginUser);
		}
		User user = loginUser.getUser();
		BeanUtils.copyProperties(form, user);
		System.out.println(user);
		userService.update(user);
		return "redirect:/item/showList";
	}
}
