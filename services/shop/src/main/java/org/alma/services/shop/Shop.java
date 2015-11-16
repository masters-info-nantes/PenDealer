package org.alma.services.shop;

import akka.actor.*;
import akka.actor.Status.Failure;
import akka.event.*;

import eventstore.*;
import eventstore.j.*;
import eventstore.tcp.ConnectionActor;

import java.net.InetSocketAddress;
import java.util.UUID;

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

	private ActorSystem esSystem;
	private ActorRef esConnection;

	public Shop() throws AxisFault {
		this.supplier = new SupplierStub("http://localhost:9763/services/Supplier/");
		this.bank = new BankStub("http://localhost:9763/services/Bank/");
		this.cart = new HashMap<>();		
	}

	public String initEventStore(){
		this.esSystem = ActorSystem.create();

        Settings settings = new SettingsBuilder()
                .address(new InetSocketAddress("127.0.0.1", 2112))
                .defaultCredentials("admin", "changeit")
                .build();

        this.esConnection = this.esSystem.actorOf(ConnectionActor.getProps(settings));

        return "ok";
	}

	public String writeEventTest(){
		ActorRef writeResult = this.esSystem.actorOf(Props.create(WriteResult.class));

        EventData event = new EventDataBuilder("my-event")
                .eventId(UUID.randomUUID())
                .data("my event data")
                .metadata("my first event")
                .build();

        WriteEvents writeEvents = new WriteEventsBuilder("my-stream")
                .addEvent(event)
                .expectAnyVersion()
                .build();

        this.esConnection.tell(writeEvents, writeResult);
        return "write";
	}

    public static class WriteResult extends UntypedActor {
        final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

        public void onReceive(Object message) throws Exception {
        	System.out.println("coucou");
            if (message instanceof WriteEventsCompleted) {
                final WriteEventsCompleted completed = (WriteEventsCompleted) message;
                log.info("range: {}, position: {}", completed.numbersRange(), completed.position());
            } else if (message instanceof Status.Failure) {
                final Status.Failure failure = ((Status.Failure) message);
                final EsException exception = (EsException) failure.cause();
                log.error(exception, exception.toString());
            } else
                unhandled(message);

            context().system().shutdown();
        }
    }

	public String readEventTest(){
        ActorRef readResult = this.esSystem.actorOf(Props.create(ReadResult.class));

        ReadEvent readEvent = new ReadEventBuilder("my-stream")
                .first()
                .resolveLinkTos(false)
                .requireMaster(true)
                .build();

        this.esConnection.tell(readEvent, readResult);

        return "coucou";
	}

    public static class ReadResult extends UntypedActor {
        final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

        public void onReceive(Object message) throws Exception {
        	        	System.out.println("coucou1111");
            if (message instanceof ReadEventCompleted) {
                final ReadEventCompleted completed = (ReadEventCompleted) message;
                final Event event = completed.event();
                log.info("event: {}", event);
            } else if (message instanceof Failure) {
                final Failure failure = ((Failure) message);
                final EsException exception = (EsException) failure.cause();
                log.error(exception, exception.toString());
            } else
                unhandled(message);

            context().system().shutdown();
        }
    }

	public String getProductDetails(String productReference) throws AxisFault, RemoteException {
		GetProductDetails getCall = new GetProductDetails();
		getCall.setProductReference(productReference);

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

	    return allProductOrdered;	    
	}
}