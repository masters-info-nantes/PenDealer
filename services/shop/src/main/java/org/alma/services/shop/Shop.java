package org.alma.services.shop;

import java.util.ArrayList;
import java.util.Collection;
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

	private Map<String, CartItem> cart;

	private EventStore eventStore;

	public Shop() throws AxisFault {
		this.supplier = new SupplierStub("http://localhost:9763/services/Supplier/");
		this.bank = new BankStub("http://localhost:9763/services/Bank/");

		this.cart = new HashMap<>();		
		//this.eventStore = new EventStore();
	}

	public Product GetProduct(String productReference) throws AxisFault, RemoteException {
		GetProduct getCall = new GetProduct();
		getCall.setProductReference(productReference);

		//this.eventStore.write("getProductDetails", productReference);

		return this.supplier.getProduct(getCall).get_return();
	}

	public Product[] GetProductsList() throws RemoteException {	
		GetProductsList getCall = new GetProductsList();
		return this.supplier.getProductsList(getCall).get_return();
	}

	public boolean AddToCart(String productReference) throws RemoteException {		

		CartItem item = this.cart.get(productReference);

		if(item == null){
			GetProduct getCall = new GetProduct();
			getCall.setProductReference(productReference);

			Product product = this.supplier.getProduct(getCall).get_return();
			item = new CartItem(product);
		}

		Integer orderedQty = item.getQuantity() + 1;

		GetProductAvailability getCall = new GetProductAvailability();
		getCall.setProductReference(productReference);		

		if(this.supplier.getProductAvailability(getCall).get_return() >= orderedQty){
			this.cart.put(productReference, item);
			//this.eventStore.write("addToCart", productReference);	
					
			return true;
		}

		return false;
	}

	public ArrayList<CartItem> GetCart() {
		return new ArrayList<>(this.cart.values());
	}

	public void RemoveFromCart(String productReference){
		CartItem item = this.cart.get(productReference);

		if(item.getQuantity() > 1){
			item.removeOne();			
		}
		else {
			this.cart.remove(item);
		}

		//this.eventStore.write("removeFromCart", productReference);
	}

	public boolean ProcessOrder() throws AxisFault, RemoteException {

		// Payment with bank service
		int totalPrice = 0;

		Iterator it = this.cart.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry current = (Map.Entry)it.next();	        

	        CartItem item = (CartItem) current.getValue();
	        totalPrice += item.getTotalPrice();
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
	        Map.Entry current = (Map.Entry) it.next();
	        CartItem item = (CartItem) current.getValue();

	        OrderProduct orderCall = new OrderProduct();
	        orderCall.setProductReference(item.getProduct().getReference());
	        orderCall.setQuantity(item.getQuantity());

	        allProductOrdered = this.supplier.orderProduct(orderCall).get_return();
	    }

	    if(!allProductOrdered){
	    	// TODO: Refund, cancel supplier command, what policy ?
	    }
			
		//this.eventStore.write("processOrder", String.valueOf(totalPrice));	

	    return allProductOrdered;	    
	}
}