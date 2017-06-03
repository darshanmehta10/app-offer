package com.test.offer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.test.offer.model.Offer;

/**
 * 
 * @author Darshan Mehta
 * This interface is a part of service layer. Methods of this interface are implemented by
 * OfferServiceImpl to perform save and get operations.
 */
public interface OfferService {
	
	/**
	 * Saves the Offer into database
	 * @param offer the offer object
	 */
	public void save(Offer offer);
	
	/**
	 * Returns paged response of available offers
	 * @param pageable the page request
	 * @return paged response of offers
	 */
	public Page<Offer> getOffers(Pageable pageable);

}
