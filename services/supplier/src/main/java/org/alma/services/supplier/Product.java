package org.alma.services.supplier;

/*
	Product stocked by the supplier
*/
public class Product {

	private String reference;

	private String name;
	private String details;

	private double price;

	public Product(String reference, String name, String details, double price){
		this.reference = reference;
		this.name = name;
		this.details = details;
		this.price = price;
	}

	public String getReference(){
		return this.reference;
	}

	public String getName(){
		return this.name;
	}

	public String getDetails(){
		return this.details;
	}

	public double getPrice(){
		return this.price;
	}

	public void setPrice(double price){
		this.price = price;
	}
}