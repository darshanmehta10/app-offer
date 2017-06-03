package com.test.offer.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferRepositoryTest {
	
	private OfferRepository repository;
	
	@Autowired
	private OfferRepository offerRepository;

	@Test
	public void repositoryExists(){
		assertNull(repository);
	}
	
	@Test
	public void repositoryLoads() {
		assertNotNull(offerRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void saveOfferNullFields(){
		Offer offer = new Offer();
		offerRepository.save(offer);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void saveOfferNullPriceCurrency(){
		Offer offer = new Offer();
		offer.setName("test");
		offer.setDescription("test");
		offerRepository.save(offer);
	}
	
	@Test
	public void saveOfferWithProducts(){
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
		
		offerRepository.save(offer);
		assertEquals(1, offerRepository.findAll().size());
		List<Product> products = offer.getProducts();
		assertEquals(2, products.size());
		assertTrue(products.contains(toothBrush));
		assertTrue(products.contains(toothPaste));
	}
	
	@Test
	public void findAllWithPageOne(){
		
		Product toothBrush = new Product();
		toothBrush.setCompany("Signal");
		toothBrush.setName("Toothbrush");
		
		Product toothPaste = new Product();
		toothPaste.setCompany("Signal");
		toothPaste.setName("Toothpaste");
		
		Offer offer = new Offer();
		offer.setName("Test offer 1");
		offer.setDescription("This is test offer 1");
		offer.setCurrency("GBP");
		offer.setPrice(10d);
		offer.setProducts(Arrays.asList(toothBrush));
		offerRepository.save(offer);
		
		offer = new Offer();
		offer.setName("Test offer 2");
		offer.setDescription("This is test offer 2");
		offer.setCurrency("USD");
		offer.setPrice(20d);
		offer.setProducts(Arrays.asList(toothPaste));
		offerRepository.save(offer);
		
		Page<Offer> offers = offerRepository.findAll(new PageRequest(0, 1, new Sort("id")));
		
		assertEquals(offers.getSize(), 1);
		assertEquals(offers.getContent().get(0).getName(), "Test offer 1");
	}
	
	@Test
	public void findAllWithPageTwo(){
		
		Product toothBrush = new Product();
		toothBrush.setCompany("Signal");
		toothBrush.setName("Toothbrush");
		
		Product toothPaste = new Product();
		toothPaste.setCompany("Signal");
		toothPaste.setName("Toothpaste");
		
		Offer offer = new Offer();
		offer.setName("Test offer 1");
		offer.setDescription("This is test offer 1");
		offer.setCurrency("GBP");
		offer.setPrice(10d);
		offer.setProducts(Arrays.asList(toothBrush));
		offerRepository.save(offer);
		
		offer = new Offer();
		offer.setName("Test offer 2");
		offer.setDescription("This is test offer 2");
		offer.setCurrency("USD");
		offer.setPrice(20d);
		offer.setProducts(Arrays.asList(toothPaste));
		offerRepository.save(offer);
		
		Page<Offer> offers = offerRepository.findAll(new PageRequest(1, 1, new Sort("id")));
		
		assertEquals(offers.getSize(), 1);
		assertEquals(offers.getContent().get(0).getName(), "Test offer 2");
	}
	
	@After
	public void removeOffers(){
		offerRepository.deleteAll();
	}

}
