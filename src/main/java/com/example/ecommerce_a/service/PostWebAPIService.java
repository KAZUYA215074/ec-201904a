package com.example.ecommerce_a.service;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.ecommerce_a.domain.CreditcardInfo;
import com.example.ecommerce_a.domain.ResponceCreditcardServerInfo;

/**
 * WebAPIにPOSTを行うサービス.
 * 
 * @author Makoto
 *
 */
@Service
public class PostWebAPIService {

	/** Creditcard APIのエンドポイント */
	private final static String CREDITCARD_SERVER_ENDPOINT = "http://153.126.174.131:8080/sample-credit-card-web-api/credit-card/payment";

	/**
	 * Creditcard APIにカード情報をPOSTする
	 */
	public ResponceCreditcardServerInfo postCreditcardServer(CreditcardInfo creditcardInfo) {
		HttpEntity<CreditcardInfo> request = new HttpEntity<>(creditcardInfo);
		ResponceCreditcardServerInfo responceCreditcardServerInfo = new ResponceCreditcardServerInfo();
		RestTemplate restTemplate = new RestTemplate();
		responceCreditcardServerInfo = restTemplate.postForObject(CREDITCARD_SERVER_ENDPOINT, request,
				ResponceCreditcardServerInfo.class);
		return responceCreditcardServerInfo;
	}

}
