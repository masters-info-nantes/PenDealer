public class Shop {

	// TODO: Extern services, how to call them ?
	private Supplier supplier;
	private Bank bank;

	private Map<String, Integer> cart;

	public Shop(){
		this.supplier = new Supplier();
		this.cart = new HashMap<>();
	}

	public String getProductDetails(String productReference){
		this.supplier.getProductDetails(productReference);
	}

	public List<Product> getProductsList(){
		this.supplier.getProductsList();
	}

	public boolean addToCart(String productReference){
		
		Integer orderedQty = this.cart.get(productReference);
		orderedQty = (orderedQty == null) ? 1 : orderedQty + 1;

		if(this.supplier.getProductAvailability(productReference) >= orderedQty){
			this.cart.put(productReference, orderedQty);
			return true;
		}

		return false;
	}

	public void removeFromCart(String productReference){
		Integer orderedQty = this.cart.get(productReference);

		if(orderedQty != null){
			this.cart.put(productReference, orderedQty - 1);
		}
	}

	public boolean processOrder(){

		// Payment with bank service
		int totalPrice = 0;

		Iterator it = this.cart.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry currentProduct = (Map.Entry)it.next();

	        totalPrice += 
	        	this.supplier.getProductPrice(currentProduct.getKey()) * currentProduct.getValue();
	    }
		
	    if(!this.bank.makeOnlinePayment(totalPrice)){  // Paiement fails
	    	return false;
	    }

		// Order with supplier service	
		boolean allProductOrdered = true;

		Iterator it = this.cart.entrySet().iterator();
	    while (allProductOrdered && it.hasNext()) {
	        Map.Entry currentProduct = (Map.Entry)it.next();
	        allProductOrdered = this.supplier.orderProduct(currentProduct.getKey(), currentProduct.getValue());
	    }

	    if(!allProductOrdered){
	    	// TODO: Refund, cancel supplier command, what policy ?
	    }

	    return allProductOrdered;
	}
}