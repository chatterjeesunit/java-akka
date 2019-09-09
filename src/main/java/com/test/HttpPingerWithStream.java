package com.test;

import akka.actor.AbstractActor;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.pattern.PipeToSupport;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import scala.concurrent.ExecutionContextExecutor;

import java.util.concurrent.CompletionStage;

import static akka.pattern.Patterns.pipe;

class HttpPingerWithStream extends AbstractActor {
    final Http http = Http.get(context().system());
    final ExecutionContextExecutor dispatcher = context().dispatcher();
    final Materializer materializer = ActorMaterializer.create(context());

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, url -> {
                    PipeToSupport.PipeableCompletionStage stage = pipe(fetch(url), dispatcher).to(self());
                    stage.future().whenComplete((response, error) -> {
                        HttpResponse httpResponse = (HttpResponse) response;
                        if (error == null) {
                            System.out.println(url + " : SUCCESS (" + httpResponse.status() + ")");
                        } else {
                            System.out.println(url + " : FAILED");
                        }

                        httpResponse.discardEntityBytes(materializer);
                    });
                })
                .build();


    }

    CompletionStage<HttpResponse> fetch(String url) {
        return http.singleRequest(HttpRequest.create(url));
    }
}