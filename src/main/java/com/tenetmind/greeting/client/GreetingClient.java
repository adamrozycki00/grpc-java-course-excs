package com.tenetmind.greeting.client;

import com.tenetmind.greet.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.tenetmind.greet.GreetServiceGrpc.newBlockingStub;


public class GreetingClient {

    public static void main(String[] args) {
        System.out.println("Hello, I'm a gRPC client!");

        GreetingClient main = new GreetingClient();
        main.run();
    }

    private void run() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

//        doUnaryCall(channel);
//        doServerStreamingCall(channel);
//        doClientStreamingCall(channel);
        doBiDiStreamingCall(channel);

        System.out.println("Shutting down the channel");
        channel.shutdown();
    }

    private void doUnaryCall(ManagedChannel channel) {
        GreetServiceGrpc.GreetServiceBlockingStub blockingStub = newBlockingStub(channel);

        Greeting greeting = Greeting.newBuilder()
                .setFirstName("Adam")
                .setLastName("Rozycki")
                .build();

        GreetRequest greetRequest = GreetRequest.newBuilder()
                .setGreeting(greeting)
                .build();

        GreetResponse greetResponse = blockingStub.greet(greetRequest);

        System.out.println(greetResponse.getResult());
    }

    private void doServerStreamingCall(ManagedChannel channel) {
        GreetServiceGrpc.GreetServiceBlockingStub blockingStub = newBlockingStub(channel);

        Greeting greeting = Greeting.newBuilder()
                .setFirstName("Adam")
                .setLastName("Rozycki")
                .build();

        GreetManyTimesRequest greetManyTimesRequest = GreetManyTimesRequest.newBuilder()
                .setGreeting(greeting)
                .build();

        blockingStub.greetManyTimes(greetManyTimesRequest)
                .forEachRemaining(singleResponse -> System.out.println(singleResponse.getResult()));
    }

    private void doClientStreamingCall(ManagedChannel channel) {
        GreetServiceGrpc.GreetServiceStub asyncStub = GreetServiceGrpc.newStub(channel);

        LongGreetRequest request1 = getLongGreetRequest("Adam");
        LongGreetRequest request2 = getLongGreetRequest("Hanna");
        LongGreetRequest request3 = getLongGreetRequest("John");
        List<LongGreetRequest> listOfRequests = List.of(request1, request2, request3);

        CountDownLatch latch = new CountDownLatch(1);

        //we get a request observer (using longGreet() method) for streaming our requests
        StreamObserver<LongGreetRequest> requestObserver =
                asyncStub.longGreet(new StreamObserver<>() {
                    @Override
                    public void onNext(LongGreetResponse value) {
                        //we get a response from the server
                        //onNext() will be called only once
                        System.out.println("Received a response from the server. The response is:");
                        System.out.println(value.getResult());
                    }

                    @Override
                    public void onError(Throwable t) {
                        //we get an error from the server
                    }

                    @Override
                    public void onCompleted() {
                        //the server is done sending us data
                        //onCompleted() will be called right after onNext()
                        System.out.println("Server has completed responding");
                        latch.countDown();
                    }
                });

        //the client sends our requests to the server one by one
        listOfRequests.forEach(requestObserver::onNext);

        //the client tells the server that is done sending data
        requestObserver.onCompleted();

        try {
            latch.await(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doBiDiStreamingCall(ManagedChannel channel) {
        GreetServiceGrpc.GreetServiceStub asyncStub = GreetServiceGrpc.newStub(channel);

        final List<String> names = List.of("Adam", "Monica", "Samantha", "Patricia", "Helga",
                "Stephen", "John", "Lucy", "Frank", "Sydney");

        final List<GreetEveryoneRequest> listOfRequests = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            int randomIndex = new Random().nextInt(names.size());
            listOfRequests.add(
                    GreetEveryoneRequest.newBuilder()
                            .setGreeting(Greeting.newBuilder().setFirstName(names.get(randomIndex)))
                            .build());
        }

        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<GreetEveryoneRequest> requestObserver = asyncStub.greetEveryone(new StreamObserver<>() {
            @Override
            public void onNext(GreetEveryoneResponse value) {
                System.out.println("Response from the server: " + value.getResult());
            }

            @Override
            public void onError(Throwable t) {
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("The server is done sending data");
                latch.countDown();
            }
        });

        listOfRequests.forEach(request -> {
            System.out.println("Sending: " + request.getGreeting().getFirstName());
            requestObserver.onNext(request);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        try {
            latch.await(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private LongGreetRequest getLongGreetRequest(String firstName) {
        return LongGreetRequest.newBuilder()
                .setGreeting(Greeting.newBuilder()
                        .setFirstName(firstName)
                        .build())
                .build();
    }

}
