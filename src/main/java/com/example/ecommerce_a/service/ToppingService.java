package com.example.ecommerce_a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.Topping;
import com.example.ecommerce_a.repository.ToppingRepository;

/**
 * トッピングを操作するサービス.
 * 
 * @author Makoto
 *
 */
@Service
@Transactional
public class ToppingService {

	@Autowired
	private ToppingRepository toppingRepository;

	/**
	 * 全トッピング情報を検索する.
	 * 
	 * @return 全トッピング情報一覧
	 */
	public List<Topping> findAll() {
		return toppingRepository.findAll();
	}

	/**
	 * トッピングの主キー検索.
	 * @param id トッピングのid
	 * @return トッピング
	 */
	public Topping load(Integer id) {
		Topping topping = toppingRepository.load(id);
		return topping;
	}

}
