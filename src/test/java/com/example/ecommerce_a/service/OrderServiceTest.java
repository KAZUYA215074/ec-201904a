package com.example.ecommerce_a.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ecommerce_a.domain.Order;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

	@Autowired
	private OrderService orderService;
	
	@Test
	public void test() {
		List<Order> orderList = orderService.showShoppingHistory(2);
		for(Order order:orderList) {
			System.out.println(order);
		}
	}

}
