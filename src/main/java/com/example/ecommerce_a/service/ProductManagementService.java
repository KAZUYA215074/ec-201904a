package com.example.ecommerce_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.repository.ProductManagementRepository;

@Service
@Transactional
public class ProductManagementService {
	
	@Autowired
	private ProductManagementRepository repository;
	
	public void insertPizza(Item item) {
		repository.insertPizza(item);
	}
	
	public void invisible(Integer id) {
		repository.invisible(id);
	}
	
	public void visible(Integer id) {
		repository.visible(id);
	}

}
