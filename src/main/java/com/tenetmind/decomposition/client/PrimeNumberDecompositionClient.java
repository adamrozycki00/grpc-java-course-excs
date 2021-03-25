package com.tenetmind.decomposition.client;

import com.tenetmind.decomposition.PrimeNumberDecompositionRequest;
import com.tenetmind.decomposition.PrimeNumberDecompositionServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import static com.tenetmind.decomposition.PrimeNumberDecompositionServiceGrpc.newBlockingStub;

public class PrimeNumberDecompositionClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052)
                .usePlaintext()
                .build();

        PrimeNumberDecompositionServiceGrpc.PrimeNumberDecompositionServiceBlockingStub serverStub =
                newBlockingStub(channel);


        PrimeNumberDecompositionRequest request = PrimeNumberDecompositionRequest.newBuilder()
                .setNumber(120)
                .build();

        System.out.println("Prime number decomposition for " + request.getNumber() + " is:");

        serverStub.decompose(request)
                .forEachRemaining(partialResponse -> System.out.println(partialResponse.getPartialResult()));
    }

}
