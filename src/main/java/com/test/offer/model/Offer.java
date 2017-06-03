package com.test.offer.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author Darshan Mehta
 * Model class for Offer, used to persist Offer details into database
 */
@Entity
@Table(name="offer")
public class Offer implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	@GeneratedValue
	private Long id;
	
	@NotNull(message = "{error.offer.name.required}")
	@Column(nullable = false)
	private String name;
	
	@NotNull(message = "{error.offer.description.required}")
	@Column(nullable = false)
	private String description;
	
	@NotNull(message = "{error.offer.price.required}")
	@Column(nullable = false)
	private Double price;
	
	@NotNull(message = "{error.offer.currency.required}")
	@Column(nullable = false)
	private String currency;
	
	@Valid
	@NotEmpty(message = "{error.offer.products.required}")
	@ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "offer_product", joinColumns = {@JoinColumn(name = "offer_id", referencedColumnName = "id")}, 
    	inverseJoinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")})
	private List<Product> products;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	/**
	 * Generates unique hashcode for object. Returns 0 if the object has not been persisted.
	 * 
	 * @return int hashcode
	 */
	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

	/**
	 * Checks whether two objects are equal or not, by comparing their id
	 * 
	 * @param o the object to compare against
	 * @return boolean flag indicating whether two objects are equal
	 */
	@Override
	public boolean equals(Object o) {
		if(this == o){
			return true;
		}
		return o instanceof Offer && this.id != null && this.id.equals(((Offer) o).id);
	}
}