package com.proj.products.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proj.products.dto.ProductDTO;
import com.proj.products.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {
	@Autowired
	private ProductService service;

	@GetMapping
	public ResponseEntity<Page<ProductDTO>> getAll(Pageable pageable) {
		Page<ProductDTO> dto = service.findAllPaged(pageable);
		return ResponseEntity.ok().body(dto);
	}
}
