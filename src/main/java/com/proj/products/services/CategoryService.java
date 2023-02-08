package com.proj.products.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proj.products.dto.CategoryDTO;
import com.proj.products.entities.Category;
import com.proj.products.repositories.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository repository;

	public CategoryDTO findAll()
	{
		Category entity = new Category(1L, "gardening");

		return new CategoryDTO(entity);
	}
}
