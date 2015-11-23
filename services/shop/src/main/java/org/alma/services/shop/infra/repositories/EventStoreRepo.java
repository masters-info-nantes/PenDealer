package org.alma.services.shop.infra.repositories;

import akka.actor.*;

import eventstore.*;
import eventstore.j.*;
import eventstore.tcp.ConnectionActor;

import org.alma.services.shop.api.repositories.EventRepository;
import org.alma.services.shop.api.valueobjects.IData;

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
                .address(new InetSocketAddress("localhost", 1113))
                .defaultCredentials("admin", "changeit")
                .build();

        ActorRef connection = system.actorOf(ConnectionActor.getProps(settings));
        ActorRef writeResult = system.actorOf(Props.create(WriteResult.class));

        EventData event = new EventDataBuilder(data.getValue())
                .eventId(UUID.randomUUID())
                .data(data.getValue())
                .metadata(data.getMetadata())
                .build();

        WriteEvents writeEvents = new WriteEventsBuilder("Shop")
                .addEvent(event)
                .expectAnyVersion()
                .build();

        connection.tell(writeEvents, writeResult);
    }
}
