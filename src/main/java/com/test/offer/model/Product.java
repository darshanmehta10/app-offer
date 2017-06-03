package com.test.offer.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Darshan Mehta
 * Model class for Product, used to persist Product details into database
 */
@Entity
@Table(name="product")
public class Product implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id")
	@GeneratedValue
	private Long id;
	
	@NotNull(message = "{error.product.name.required}")
	@Column(nullable = false)
	private String name;
	
	@NotNull(message = "{error.product.company.required}")
	@Column
	private String company;

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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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
		return o instanceof Product && this.id != null && this.id.equals(((Product) o).id);
	}
}