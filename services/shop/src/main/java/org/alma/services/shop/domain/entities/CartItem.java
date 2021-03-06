package org.alma.services.shop.domain.entities;

import org.alma.services.supplier.domain.services.impl.SupplierStub.*;

public class CartItem {

	private Product product;
	private int quantity;

	public CartItem(Product product){
		this.product = product;
		this.quantity = 1;		
	}

	public void addOne(){
		this.quantity++;
	}

	public void removeOne(){
		this.quantity--;
	}

	public int getQuantity(){
		return this.quantity;
	}

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	public double getTotalPrice(){
		return this.quantity * this.product.getPrice();
	}

	public Product getProduct(){
		return this.product;
	}
}