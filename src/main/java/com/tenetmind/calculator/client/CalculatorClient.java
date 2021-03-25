package com.tenetmind.calculator.client;

import com.tenetmind.calculator.CalculatorServiceGrpc;
import com.tenetmind.calculator.PrimeNumberDecompositionRequest;
import com.tenetmind.calculator.SummationRequest;
import com.tenetmind.calculator.SummationResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import static com.tenetmind.calculator.CalculatorServiceGrpc.newBlockingStub;

public class CalculatorClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50050)
                .usePlaintext()
                .build();

        CalculatorServiceGrpc.CalculatorServiceBlockingStub serverStub = newBlockingStub(channel);

        SummationRequest summationRequest = SummationRequest.newBuilder()
                .setNum1(10)
                .setNum2(3)
                .build();

        SummationResponse sum = serverStub.sum(summationRequest);

        System.out.println("The sum of " + summationRequest.getNum1() + " and " + summationRequest.getNum2() +
                " is " + sum.getResult());

        PrimeNumberDecompositionRequest decompositionRequest =
                PrimeNumberDecompositionRequest.newBuilder()
                .setNumber(120)
                .build();

        System.out.println("Prime number decomposition for " + decompositionRequest.getNumber() + " is:");

        serverStub.decompose(decompositionRequest)
                .forEachRemaining(partialResponse -> System.out.println(partialResponse.getPartialResult()));
    }

}
