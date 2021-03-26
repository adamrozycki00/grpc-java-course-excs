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
        System.out.println("Hello, I'm a gRPC client!");

        CalculatorClient main = new CalculatorClient();
        main.run();
    }

    private void run() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052)
                .usePlaintext()
                .build();

        calculateSum(channel);
        decomposeIntoPrimeNumbers(channel);

        System.out.println("Shutting down the channel");
        channel.shutdown();
    }

    private void calculateSum(ManagedChannel channel) {
        CalculatorServiceGrpc.CalculatorServiceBlockingStub blockingStub = newBlockingStub(channel);

        SummationRequest summationRequest = SummationRequest.newBuilder()
                .setNum1(10)
                .setNum2(3)
                .build();

        SummationResponse sum = blockingStub.sum(summationRequest);

        System.out.println("The sum of " + summationRequest.getNum1() + " and " + summationRequest.getNum2() +
                " is " + sum.getResult());
    }

    private void decomposeIntoPrimeNumbers(ManagedChannel channel) {
        CalculatorServiceGrpc.CalculatorServiceBlockingStub blockingStub = newBlockingStub(channel);

        PrimeNumberDecompositionRequest decompositionRequest =
                PrimeNumberDecompositionRequest.newBuilder()
                        .setNumber(120)
                        .build();

        System.out.println("Prime number decomposition for " + decompositionRequest.getNumber() + " is:");

        blockingStub.decompose(decompositionRequest)
                .forEachRemaining(partialResponse -> System.out.println(partialResponse.getPartialResult()));
    }

}
