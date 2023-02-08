package com.proj.products.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proj.products.dto.CategoryDTO;
import com.proj.products.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	@Autowired
	CategoryService service;

	@GetMapping
	public ResponseEntity<Page<CategoryDTO>> findAllPaged(Pageable pageable) {

		Page<CategoryDTO> dto = service.findAllPaged(pageable);

		return ResponseEntity.ok().body(dto);
	}

}
