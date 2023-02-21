package com.proj.products.factories;

import com.proj.products.entities.Product;

public abstract class Factory {

	public static Product createProduct() {
		return new Product(1L, "Samsumg Galaxy S6", "Nice Mobile Phone", 5735.84, null);

	}
}
