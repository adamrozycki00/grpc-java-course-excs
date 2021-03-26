package com.tenetmind.calculator.client;

import com.tenetmind.calculator.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

//        calculateSum(channel);
//        decomposeIntoPrimeNumbers(channel);
        calculateAverage(channel);

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

    private void calculateAverage(ManagedChannel channel) {
        CalculatorServiceGrpc.CalculatorServiceStub asyncStub = CalculatorServiceGrpc.newStub(channel);

        final List<Integer> inputIntegers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<ComputeAverageRequest> requests = inputIntegers.stream()
                .map(number ->
                        ComputeAverageRequest.newBuilder()
                                .setNumber(number)
                                .build())
                .collect(Collectors.toList());

        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<ComputeAverageRequest> requestObserver = asyncStub.average(new StreamObserver<>() {
            @Override
            public void onNext(ComputeAverageResponse value) {
                System.out.println("Input integers: " + inputIntegers);
                System.out.println("Average is: " + value.getResult());
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        });

        requests.forEach(requestObserver::onNext);

        requestObserver.onCompleted();

        try {
            latch.await(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
