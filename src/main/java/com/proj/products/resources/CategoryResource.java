package com.proj.products.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proj.products.dto.CategoryDTO;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	@GetMapping
	public ResponseEntity<CategoryDTO> findAll()
	{
		CategoryDTO dto = new CategoryDTO(1L, "gardening");

		return ResponseEntity.ok().body(dto);
	}

}
