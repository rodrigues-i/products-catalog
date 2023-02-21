package com.proj.products.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.proj.products.dto.ProductDTO;
import com.proj.products.entities.Product;
import com.proj.products.factories.Factory;
import com.proj.products.repositories.ProductRepository;
import com.proj.products.services.exceptions.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	@InjectMocks
	private ProductService service;
	@Mock
	private ProductRepository repository;

	private Product product;
	private PageImpl<Product> page;
	private Long existingId;
	private Long nonExistingId;

	@BeforeEach
	void setUp() {
		existingId = 1L;
		nonExistingId = 1000L;
		product = Factory.createProduct();

		page = new PageImpl<>(List.of(product));

		Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
		Mockito.when(repository.getOne(existingId)).thenReturn(product);
		Mockito.doThrow(EntityNotFoundException.class).when(repository).getOne(nonExistingId);

		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
	}

	@Test
	public void findAllPagedShouldReturnPage() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<ProductDTO> page = service.findAllPaged(pageable);

		Assertions.assertNotNull(page);

		Mockito.verify(repository).findAll(pageable);
	}

	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {
		ProductDTO dto = service.findById(existingId);
		Assertions.assertNotNull(dto);
		Mockito.verify(repository, Mockito.times(1)).findById(existingId);
	}

	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});

		Mockito.verify(repository, Mockito.times(1)).findById(nonExistingId);
	}

	@Test
	public void insertShouldReturnProductDTO() {
		Product entity = Factory.createProduct();
		ProductDTO dto = service.insert(new ProductDTO(entity));
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(dto.getName(), entity.getName());
		Assertions.assertEquals(dto.getPrice(), entity.getPrice());
	}

	@Test
	public void updateShouldReturnProductDTOWhenIdExists() {
		Product entity = Factory.createProduct();
		String originalName = entity.getName();
		ProductDTO dto = new ProductDTO(entity);
		dto.setName("New Name");
		dto = service.update(existingId, dto);
		Assertions.assertNotEquals(dto.getName(), originalName);

		Mockito.verify(repository, Mockito.times(1)).getOne(existingId);
	}

	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			ProductDTO dto = new ProductDTO(Factory.createProduct());
			service.update(nonExistingId, dto);
		});

		Mockito.verify(repository, Mockito.times(1)).getOne(nonExistingId);
	}

	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
		
		Mockito.verify(repository).deleteById(nonExistingId);
	}
}
