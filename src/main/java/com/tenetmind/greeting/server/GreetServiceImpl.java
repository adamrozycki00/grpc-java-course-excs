package com.tenetmind.greeting.server;

import com.tenetmind.greet.GreetRequest;
import com.tenetmind.greet.GreetResponse;
import com.tenetmind.greet.GreetServiceGrpc;
import com.tenetmind.greet.Greeting;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {

    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        Greeting greeting = request.getGreeting();

        String result = "Hello, " + greeting.getFirstName() + "!";
        GreetResponse response = GreetResponse.newBuilder()
                .setResult(result)
                .build();

        responseObserver.onNext(response);

        responseObserver.onCompleted();
    }

}
