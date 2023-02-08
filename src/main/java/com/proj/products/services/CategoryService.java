package com.proj.products.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proj.products.dto.CategoryDTO;
import com.proj.products.entities.Category;
import com.proj.products.repositories.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository repository;

	public Page<CategoryDTO> findAllPaged(Pageable pageable) {
		Page<Category> list = repository.findAll(pageable);
		return list.map(cat -> new CategoryDTO(cat));
	}
}
