package com.learn.akka.actors.helloakka;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

public class ActorTwo extends AbstractLoggingActor {

    static Props props() {
        return Props.create(ActorTwo.class, ActorTwo::new);
    }

    @Override
    public Receive createReceive() {

        return ReceiveBuilder.create().
            match(DemoMessage.class, msg -> {

                System.out.println(getSelf() + " : Message processed : " + msg.getMessage());

                sender().tell(new AckMessage("I am done !!!"), ActorRef.noSender());

            }).build();
    }
}
