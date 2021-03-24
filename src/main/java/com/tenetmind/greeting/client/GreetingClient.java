package com.tenetmind.greeting.client;

import com.tenetmind.greet.GreetRequest;
import com.tenetmind.greet.GreetResponse;
import com.tenetmind.greet.GreetServiceGrpc;
import com.tenetmind.greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import static com.tenetmind.greet.GreetServiceGrpc.newBlockingStub;

public class GreetingClient {

    public static void main(String[] args) {
        System.out.println("Hello, I'm a gRPC client");

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        System.out.println("Creating stub");
//        DummyServiceGrpc.DummyServiceBlockingStub syncClient = newBlockingStub(channel);
//        DummyServiceGrpc.DummyServiceFutureStub asyncClient = newFutureStub(channel);

        GreetServiceGrpc.GreetServiceBlockingStub greetClient = newBlockingStub(channel);

        Greeting greeting = Greeting.newBuilder()
                .setFirstName("Adam")
                .setLastName("Rozycki")
                .build();

        GreetRequest greetRequest = GreetRequest.newBuilder()
                .setGreeting(greeting)
                .build();

        GreetResponse greetResponse = greetClient.greet(greetRequest);

        System.out.println(greetResponse.getResult());

        System.out.println("Shutting down channel");
        channel.shutdown();
    }

}
