package org.alma;

import akka.actor.*;
import akka.actor.Status.Failure;
import akka.event.*;

import eventstore.*;
import eventstore.j.*;
import eventstore.tcp.ConnectionActor;

import java.net.InetSocketAddress;
import java.util.UUID;

public class EventStore {

	private ActorRef connection;
	private ActorRef writeResult;

	public EventStore(){
		ActorSystem system = ActorSystem.create();

		Settings settings = new SettingsBuilder()
		        .address(new InetSocketAddress("localhost", 1113))
		        .defaultCredentials("admin", "changeit")
		        .build();

		this.connection = system.actorOf(ConnectionActor.getProps(settings));
		this.writeResult = system.actorOf(Props.create(WriteResult.class));		
	}

	public void write(String eventType, String eventData){

		EventData event = new EventDataBuilder(eventType)
		        .eventId(UUID.randomUUID())
		        .data(eventData)
		        .metadata(eventData)
		        .build();		

		WriteEvents writeEvents = new WriteEventsBuilder("Shop")
		        .addEvent(event)
		        .expectAnyVersion()
		        .build();

		this.connection.tell(writeEvents, this.writeResult);	
	}

	public static class WriteResult extends UntypedActor {
	    public void onReceive(Object message) throws Exception {
	        context().system().shutdown();
	    }
	}
}

