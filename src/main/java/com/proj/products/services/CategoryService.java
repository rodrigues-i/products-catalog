package com.proj.products.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proj.products.dto.CategoryDTO;
import com.proj.products.entities.Category;
import com.proj.products.repositories.CategoryRepository;
import com.proj.products.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository repository;
	
	@Transactional
	public Page<CategoryDTO> findAllPaged(Pageable pageable) {
		Page<Category> list = repository.findAll(pageable);
		return list.map(cat -> new CategoryDTO(cat));
	}
	
	@Transactional
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not Found " + id));
		return new CategoryDTO(entity);
	}
}
