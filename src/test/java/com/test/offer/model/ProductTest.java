package com.test.offer.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProductTest {

	@Test
	public void classExists() {
		assertNotNull(new Product());
	}
	
	@Test
	public void fieldsExist() {
		Product product = new Product();
		assertNull(product.getId());
		assertNull(product.getName());
		assertNull(product.getCompany());
	}

}
