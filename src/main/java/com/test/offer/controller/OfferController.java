package com.test.offer.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.test.offer.model.Offer;
import com.test.offer.service.OfferService;

/**
 * 
 * @author Darshan Mehta
 * This class is a part of controller layer. It gets the API requests, calls service methods
 * and returns appropriate responses
 *
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, value = "/offers")
public class OfferController {
	
	@Autowired
	private OfferService offerService;

	/**
	 * After performing the validations, calls 'save' method of service class and returns the response
	 * @param request the request object
	 * @param errors the validation errors if any
	 * @return the response containing the actual object with id, if save is successful. If not, response includes array of failed validations 
	 */
	@RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Object> save(@Validated @RequestBody Offer offer, Errors errors){
		if(errors.hasErrors()){
			Map<String, Object> errorMap = new HashMap<>();
			errorMap.put("errors", errors.getAllErrors());
			return new ResponseEntity<Object>(errorMap, HttpStatus.BAD_REQUEST);
		}
		offerService.save(offer);
		return new ResponseEntity<Object>(offer, HttpStatus.OK);
	}
	
	/**
	 * Returns paged response containing available Offer objects. By default it returns first 10 offer objects sorted by id. However, ordering and size
	 * can be changed by passing appropriate arguments
	 * @param pageable the page request
	 * @return paged response of offers
	 */
	@RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Page<Offer>> get(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
		return new ResponseEntity<Page<Offer>>(offerService.getOffers(pageable), HttpStatus.OK);
	}
}
