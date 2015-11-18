package org.alma.services.shop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis2.AxisFault;
import org.alma.services.bank.BankStub;
import org.alma.services.bank.BankStub.*;
import org.alma.services.supplier.SupplierStub;
import org.alma.services.supplier.SupplierStub.*;
import java.rmi.RemoteException;

public class Shop {

	private SupplierStub supplier;
	private BankStub bank;

	private Map<String, Integer> cart;

	private EventStore eventStore;

	public Shop() throws AxisFault {
		this.supplier = new SupplierStub("http://localhost:9763/services/Supplier/");
		this.bank = new BankStub("http://localhost:9763/services/Bank/");

		this.cart = new HashMap<>();		
		this.eventStore = new EventStore();
	}

	public String getProductDetails(String productReference) throws AxisFault, RemoteException {
		GetProductDetails getCall = new GetProductDetails();
		getCall.setProductReference(productReference);

		this.eventStore.write("getProductDetails", productReference);

		return this.supplier.getProductDetails(getCall).get_return();
	}

	public Product[] getProductsList() throws RemoteException {	
		GetProductsList getCall = new GetProductsList();
		return this.supplier.getProductsList(getCall).get_return();
	}

	public boolean addToCart(String productReference) throws RemoteException {		

		Integer orderedQty = this.cart.get(productReference);
		orderedQty = (orderedQty == null) ? 1 : orderedQty + 1;

		GetProductAvailability getCall = new GetProductAvailability();
		getCall.setProductReference(productReference);		

		if(this.supplier.getProductAvailability(getCall).get_return() >= orderedQty){
			this.cart.put(productReference, orderedQty);
			this.eventStore.write("addToCart", productReference);	
					
			return true;
		}

		return false;
	}

	public void removeFromCart(String productReference){
		Integer orderedQty = this.cart.get(productReference);

		if(orderedQty != null){
			this.cart.put(productReference, orderedQty - 1);
			this.eventStore.write("removeFromCart", productReference);			
		}
	}

	public boolean processOrder() throws AxisFault, RemoteException {

		// Payment with bank service
		int totalPrice = 0;

		Iterator it = this.cart.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry currentProduct = (Map.Entry)it.next();

	        GetProductPrice getCall = new GetProductPrice();
	        getCall.setProductReference((String) currentProduct.getKey());

	        Integer price = (Integer) this.supplier.getProductPrice(getCall).get_return();
	        totalPrice += price * (Integer) currentProduct.getValue();
	    }
		

	    MakeOnlinePayment payment = new MakeOnlinePayment();
		payment.setAmount(totalPrice);
		
	    if(!this.bank.makeOnlinePayment(payment).get_return()){  // Paiement fails
	    	return false;
	    }

		// Order with supplier service	
		boolean allProductOrdered = true;

		it = this.cart.entrySet().iterator();
	    while (allProductOrdered && it.hasNext()) {
	        Map.Entry currentProduct = (Map.Entry)it.next();

	        OrderProduct orderCall = new OrderProduct();
	        orderCall.setProductReference((String) currentProduct.getKey());
	        orderCall.setQuantity((Integer) currentProduct.getValue());

	        allProductOrdered = this.supplier.orderProduct(orderCall).get_return();
	    }

	    if(!allProductOrdered){
	    	// TODO: Refund, cancel supplier command, what policy ?
	    }
			
		this.eventStore.write("processOrder", String.valueOf(totalPrice));	

	    return allProductOrdered;	    
	}
}