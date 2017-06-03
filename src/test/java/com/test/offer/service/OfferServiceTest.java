package com.test.offer.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.offer.model.Offer;
import com.test.offer.model.Product;
import com.test.offer.repository.OfferRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferServiceTest {
	
	@Autowired
	private OfferService offerService;
	
	@Autowired
	private OfferRepository offerRepository;

	@Test
	public void interfaceExists() {
		assertNotNull(offerService);
	}
	
	@Test
	public void implExists(){
		assertNotNull(new OfferServiceImpl());
	}
	
	@Test(expected = NullPointerException.class)
	public void hasSaveMethod(){
		new OfferServiceImpl().save(new Offer());
	}
	
	@Test(expected = NullPointerException.class)
	public void hasGetMethod(){
		new OfferServiceImpl().getOffers(new PageRequest(1, 1));
	}
	
	@Test
	public void interfaceLoads(){
		assertNotNull(offerService);
	}
	
	@Test
	public void saveAndGet(){
		Product toothBrush = new Product();
		toothBrush.setCompany("Signal");
		toothBrush.setName("Toothbrush");
		
		Product toothPaste = new Product();
		toothPaste.setCompany("Signal");
		toothPaste.setName("Toothpaste");
		
		Offer offer = new Offer();
		offer.setName("Test offer");
		offer.setDescription("This is test offer");
		offer.setCurrency("GBP");
		offer.setPrice(10d);
		
		offer.setProducts(Arrays.asList(toothBrush, toothPaste));
		
		offerService.save(offer);
		
		Page<Offer> offers = offerService.getOffers(new PageRequest(0, 10));
		assertEquals(1, offers.getNumberOfElements());
		List<Product> products = offer.getProducts();
		assertEquals(2, products.size());
		assertTrue(products.contains(toothBrush));
		assertTrue(products.contains(toothPaste));
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void saveAndGetWithoutProductWithSort(){
		
		Offer offer = new Offer();
		offer.setName("Test offer 1");
		offer.setDescription("This is test offer 1");
		offer.setCurrency("GBP");
		offer.setPrice(10d);
		offerService.save(offer);
		
		offer = new Offer();
		offer.setName("Test offer 2");
		offer.setDescription("This is test offer 2");
		offer.setCurrency("USD");
		offer.setPrice(20d);
		offerService.save(offer);
		
		Page<Offer> offers = offerService.getOffers(new PageRequest(0, 1, new Sort("id")));
		
		assertEquals(offers.getSize(), 1);
		assertEquals(offers.getContent().get(0).getName(), "Test offer 1");
	}
	
	@Test
	public void saveAndGetWithProductWithSort(){
		
		Product toothBrush = new Product();
		toothBrush.setCompany("Signal");
		toothBrush.setName("Toothbrush");
		
		Offer offer = new Offer();
		offer.setName("Test offer 1");
		offer.setDescription("This is test offer 1");
		offer.setCurrency("GBP");
		offer.setPrice(10d);
		offer.setProducts(Arrays.asList(toothBrush));
		offerService.save(offer);
		
		toothBrush = new Product();
		toothBrush.setCompany("Signal");
		toothBrush.setName("Toothbrush 2");
		
		offer = new Offer();
		offer.setName("Test offer 2");
		offer.setDescription("This is test offer 2");
		offer.setCurrency("USD");
		offer.setPrice(20d);
		offer.setProducts(Arrays.asList(toothBrush));
		offerService.save(offer);
		
		Page<Offer> offers = offerService.getOffers(new PageRequest(0, 1, new Sort("id")));
		
		assertEquals(offers.getSize(), 1);
		assertEquals(offers.getContent().get(0).getName(), "Test offer 1");
	}
	
	@After
	public void deleteAll(){
		offerRepository.deleteAll();
	}
	
}
