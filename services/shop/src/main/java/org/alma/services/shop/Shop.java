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
import org.alma.services.bourse.BourseStub;
import org.alma.services.bourse.BourseStub.*;
import org.alma.services.supplier.SupplierStub;
import org.alma.services.supplier.SupplierStub.*;

import java.rmi.RemoteException;

public class Shop {

	private SupplierStub supplier;
	private BankStub bank;
	private BourseStub bourse;

	private Map<String, CartItem> cart;

	private EventStore eventStore;

	public Shop() throws AxisFault {
		this.supplier = new SupplierStub("http://localhost:9763/services/Supplier/");
		this.bank = new BankStub("http://localhost:9763/services/Bank/");
		this.bourse = new BourseStub("http://localhost:9763/services/Bourse/");	

		this.cart = new HashMap<String, CartItem>();

		//this.eventStore = new EventStore();
	}

	private double convertPrice(double price, String currency) throws RemoteException {
		Convert convertCall = new Convert();
		convertCall.setCurrency(currency);
		convertCall.setValue(price);

		return this.bourse.convert(convertCall).get_return();
	}

	public String[] GetCurrencies() throws RemoteException {
		GetCurrencies getCall = new GetCurrencies();
		return this.bourse.getCurrencies(getCall).get_return();		
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

	public List<CartItem> GetCart(String currency) throws RemoteException {
		List<CartItem> items = new ArrayList<CartItem>(this.cart.values());
		List<CartItem> newList = new ArrayList<CartItem>();

		for(CartItem item : items){
			Product product = item.getProduct();

			Product newProduct = this.GetProduct(product.getReference());
			newProduct.setPrice(convertPrice(product.getPrice(), currency));

			CartItem cartItem = new CartItem(newProduct);
			cartItem.setQuantity(item.getQuantity());

			newList.add(cartItem);
		}

		return newList;
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