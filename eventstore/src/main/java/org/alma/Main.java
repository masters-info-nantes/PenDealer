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
		EventStore store = new EventStore();
		store.write("Main", "coucou");
	}

	public String service(){
		EventStore store = new EventStore();
		store.write("Main", "coucou");

		return "done";
	}
}
