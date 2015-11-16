package org.alma;

import akka.actor.*;
import akka.actor.Status.Failure;
import akka.event.*;

import eventstore.*;
import eventstore.j.*;
import eventstore.tcp.ConnectionActor;

import java.net.InetSocketAddress;
import java.util.UUID;

public class Main {
	public static void main(String[] args){
		final ActorSystem system = ActorSystem.create();
		final Settings settings = new SettingsBuilder()
		        .address(new InetSocketAddress("localhost", 2113))
		        .defaultCredentials("admin", "changeit")
		        .build();
		final ActorRef connection = system.actorOf(ConnectionActor.getProps(settings));
/*
		// WRITE
		final ActorRef writeResult = system.actorOf(Props.create(WriteResult.class));

		final EventData event = new EventDataBuilder("my-event")
		        .eventId(UUID.randomUUID())
		        .data("my event data")
		        .metadata("my first event")
		        .build();

		final WriteEvents writeEvents = new WriteEventsBuilder("my-stream")
		        .addEvent(event)
		        .expectAnyVersion()
		        .build();

		connection.tell(writeEvents, writeResult);

		// READ
		final ActorRef readResult = system.actorOf(Props.create(ReadResult.class));

		final ReadEvent readEvent = new ReadEventBuilder("my-stream")
		        .first()
		        .resolveLinkTos(false)
		        .requireMaster(true)
		        .build();

		connection.tell(readEvent, readResult);
*/		
	}

	public static class ReadResult extends UntypedActor {
	    final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	    public void onReceive(Object message) throws Exception {
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

	public static class WriteResult extends UntypedActor {
	    final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	    public void onReceive(Object message) throws Exception {
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
}
