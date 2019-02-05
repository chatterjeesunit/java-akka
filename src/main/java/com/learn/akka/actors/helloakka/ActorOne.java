package com.learn.akka.actors.helloakka;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class ActorOne extends AbstractLoggingActor {

    static Props props() {
        return Props.create(ActorOne.class, ActorOne::new);
    }

    @Override
    public Receive createReceive() {

//        log().info("Actor : {}, Sender : {}", getSelf(), getSender() );
        return receiveBuilder()
            .match(DemoMessage.class, msg -> {
                System.out.println(getSelf() + " : Message processed : " + msg.getMessage());

                ActorRef actorTwo = getContext().actorOf(ActorTwo.props(), "actorTwo");

                actorTwo.tell(new DemoMessage("Welcome to Akka...."), self());


            })
            .match(AckMessage.class, msg -> {
                System.out.println(getSelf() + " : Acknowledge Recieved : " + msg.getMessage());
            }).build();
    }
}
