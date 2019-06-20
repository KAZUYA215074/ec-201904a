package com.example.ecommerce_a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.Topping;
import com.example.ecommerce_a.repository.ToppingRepository;

@Service
@Transactional
public class ToppingService {

	@Autowired
	private ToppingRepository toppingRepository;

	public List<Topping> findAll() {
		return toppingRepository.findAll();
	}

}
