package com.learn.akka.actors.timer;

import akka.actor.AbstractActorWithTimers;
import akka.actor.Props;

import java.time.Duration;
import java.util.Date;

public class TimerActor extends AbstractActorWithTimers {

    static Props props() {
        return Props.create(TimerActor.class, TimerActor::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(StartMessage.class, msg -> {
                    getTimers().startPeriodicTimer("TimerActor", "MyTickMessage", Duration.ofSeconds(1));
                    System.out.println("Starting Timer Actor :" + new Date().toString());
                    Thread.sleep(10000);
                    System.out.println("Ending Timer Actor :" + new Date().toString());
                })
                .matchEquals("MyTickMessage", msg -> System.out.println("Tick message : " + new Date().toString()))
                .build();
    }
}


class StartMessage {

}
