package com.example.ecommerce_a.service;

import org.springframework.web.client.RestTemplate;

import com.example.ecommerce_a.domain.ResponceCreditCardServerInfo;

/**
 * WebAPIにPOSTを行うサービス.
 * 
 * @author Makoto
 *
 */
public class PostWebAPIService {

	/**
	 * CreditCard APIにカード情報をPOSTする
	 */
	public void PostCreditcardServer() {
		RestTemplate restTemplate = new RestTemplate();
		ResponceCreditCardServerInfo responce = restTemplate.getForObject("http://153.126.174.131:8080/sample-credit-card-web-api/credit-card/payment ", ResponceCreditCardServerInfo.class);
	}

}
