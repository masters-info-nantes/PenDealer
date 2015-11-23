package org.alma.services.supplier.infra.repositories;

import akka.actor.*;

import eventstore.*;
import eventstore.j.*;
import eventstore.tcp.ConnectionActor;

import org.alma.services.supplier.api.repositories.EventRepository;
import org.alma.services.supplier.api.valueobjects.IData;

import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * Created by jeremy on 23/11/15.
 */
public class EventStoreRepo implements EventRepository {
    @Override
    public void sendEvent(IData data) {
        ActorSystem system = ActorSystem.create();

        Settings settings = new SettingsBuilder()
                .address(new InetSocketAddress("127.0.0.1", 1113))
                .defaultCredentials("admin", "changeit")
                .build();

        ActorRef connection = system.actorOf(ConnectionActor.getProps(settings));
        ActorRef writeResult = system.actorOf(Props.create(WriteResult.class));

        EventData event = new EventDataBuilder(data.getValue())
                .eventId(UUID.randomUUID())
                .data(data.getValue())
                .metadata(data.getMetadata())
                .build();

        WriteEvents writeEvents = new WriteEventsBuilder("Supplier")
                .addEvent(event)
                .expectAnyVersion()
                .build();

        connection.tell(writeEvents, writeResult);
    }
}
