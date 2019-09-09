package com.test;

import akka.actor.AbstractActor;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.io.Tcp;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;

import java.util.concurrent.CompletionStage;

class HttpPinger extends AbstractActor {
    final Http http = Http.get(context().system());
    final Materializer materializer = ActorMaterializer.create(context());


    @Override
    public void preStart() throws Exception {
        super.preStart();
        
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, url -> {
                    HttpRequest httpRequest = HttpRequest.create(url);
                    CompletionStage<HttpResponse> responseStage = http.singleRequest(httpRequest);

                    responseStage.whenCompleteAsync((response, error) -> {
                        HttpResponse httpResponse = (HttpResponse)response;
                        if(error == null) {
                            System.out.println(url + " : SUCCESS (" + httpResponse.status() + ")");
                        }
                        else {
                            System.out.println(url + " : FAILED");
                        }

                        httpResponse.discardEntityBytes(materializer);
                    });
                })
                .build();



    }
}