package org.alma.services.bank.infra.repositories;

import akka.actor.Status;
import akka.actor.UntypedActor;
import eventstore.EsException;
import eventstore.WriteEventsCompleted;

/**
 * Created by jeremy on 23/11/15.
 */
public class WriteResult extends UntypedActor {

    @SuppressWarnings("deprecation")
    public void onReceive(Object message) throws Exception {
        if (message instanceof WriteEventsCompleted) {
            final WriteEventsCompleted completed = (WriteEventsCompleted) message;
            //log.info("range: {}, position: {}", completed.numbersRange(), completed.position());
        }
        else if (message instanceof Status.Failure) {
            final Status.Failure failure = ((Status.Failure) message);
            final EsException exception = (EsException) failure.cause();
            //log.error(exception, exception.toString());
        }
        else
            unhandled(message);

        context().system().shutdown();
    }
}
