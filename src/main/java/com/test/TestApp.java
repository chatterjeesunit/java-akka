package com.test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Scheduler;

public class TestApp {

    public static void main(String[] args) {

        ActorSystem actorSystem = ActorSystem.create("Test-HTTP");

        ActorRef httpActor = actorSystem.actorOf(Props.create(HttpPingerWithStream.class, HttpPingerWithStream::new), "http-pinger");


        httpActor.tell("http://www.google.com", ActorRef.noSender());


        httpActor.tell("http://doc.akka.io", ActorRef.noSender());


//        httpActor.tell("http://www.ggogle.coin", ActorRef.noSender());

        
    }
}
