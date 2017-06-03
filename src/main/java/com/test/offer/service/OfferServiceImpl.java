package com.test.offer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.test.offer.model.Offer;
import com.test.offer.repository.OfferRepository;

/**
 * 
 * @author Darshan Mehta
 * This class is a part of service layer. Provides concrete implementations for OfferService interface methods
 *
 */
@Component
public class OfferServiceImpl implements OfferService{
	
	@Autowired
	private OfferRepository offerRepository;

	/**
	 * Saves the Offer into database
	 * @param offer the offer object
	 */
	@Override
	public void save(Offer offer) {
		offerRepository.save(offer);
	}

	/**
	 * Returns paged response of available offers
	 * @param pageable the page request
	 * @return paged response of offers
	 */
	@Override
	public Page<Offer> getOffers(Pageable pageable) {
		return offerRepository.findAll(pageable);
	}

}
