package com.example.ecommerce_a.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/footer")
public class FooterController {
	@RequestMapping("/company")
	public String company() {
		return "company_profile";
	}
	

}
