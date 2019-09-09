package com.learn.akka.actors.timer;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.learn.akka.actors.helloakka.ActorOne;

public class TimerApp {

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("demo");

        ActorRef actorOne = actorSystem.actorOf(TimerActor.props(), "timerActor");

        actorOne.tell(new StartMessage(), ActorRef.noSender());

    }
}
