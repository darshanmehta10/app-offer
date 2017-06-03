package com.test.offer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.offer.model.Offer;

/**
 * @author Darshan Mehta
 * Persistence layer for Offer. Used to perform CRUD operations on Offer objects
 */
public interface OfferRepository extends JpaRepository<Offer, Long>{

}
