package com.test.offer.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class OfferTest {

	@Test
	public void classExists() {
		assertNotNull(new Offer());
	}
	
	@Test
	public void fieldsExist() {
		Offer offer = new Offer();
		assertNull(offer.getId());
		assertNull(offer.getName());
		assertNull(offer.getPrice());
		assertNull(offer.getDescription());
		assertNull(offer.getCurrency());
	}
	
	@Test
	public void productsExist(){
		Offer offer = new Offer();
		assertNull(offer.getProducts());
	}

}
