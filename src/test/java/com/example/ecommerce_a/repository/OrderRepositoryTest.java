package com.example.ecommerce_a.repository;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ecommerce_a.domain.Order;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderRepositoryTest {
	
	@Autowired
	private OrderRepository orderRepository;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Order order = orderRepository.findByJoinedOrderByUserIdAndStatus(1);
		System.out.println(order);
	}

}
