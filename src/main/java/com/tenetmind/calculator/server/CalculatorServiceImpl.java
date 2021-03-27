package com.tenetmind.calculator.server;

import com.tenetmind.calculator.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.OptionalDouble;

public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {

    @Override
    public void sum(SummationRequest request, StreamObserver<SummationResponse> responseObserver) {
        SummationResponse response = SummationResponse.newBuilder()
                .setResult(request.getNum1() + request.getNum2())
                .build();

        responseObserver.onNext(response);

        responseObserver.onCompleted();
    }

    @Override
    public void decompose(PrimeNumberDecompositionRequest request,
                          StreamObserver<PrimeNumberDecompositionResponse> responseObserver) {
        int number = request.getNumber();
        int prime = 2;

        while (number > 1) {
            if (number % prime == 0) {
                responseObserver.onNext(PrimeNumberDecompositionResponse.newBuilder()
                        .setPartialResult(prime)
                        .build());
                number /= prime;
            } else {
                prime++;
            }
        }

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<ComputeAverageRequest> average(StreamObserver<ComputeAverageResponse> responseObserver) {
        return new StreamObserver<>() {
            final ArrayList<Integer> integers = new ArrayList<>();

            @Override
            public void onNext(ComputeAverageRequest value) {
                integers.add(value.getNumber());
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onCompleted() {
                OptionalDouble optionalAverage = integers.stream()
                        .mapToInt(i -> i)
                        .average();
                double result = optionalAverage.orElse(0);
                responseObserver.onNext(ComputeAverageResponse.newBuilder()
                        .setResult(result)
                        .build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<FindMaximumRequest> maximum(StreamObserver<FindMaximumResponse> responseObserver) {
        return new StreamObserver<>() {
            int maximum = Integer.MIN_VALUE;

            @Override
            public void onNext(FindMaximumRequest value) {
                int number = value.getNumber();

                if (number > maximum) {
                    maximum = number;
                    FindMaximumResponse response = FindMaximumResponse.newBuilder()
                            .setMaximum(maximum)
                            .build();
                    responseObserver.onNext(response);
                }
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void squareRoot(SquareRootRequest request, StreamObserver<SquareRootResponse> responseObserver) {
        final int number = request.getNumber();

        if (number >= 0) {
            double root = Math.sqrt(number);
            responseObserver.onNext(SquareRootResponse.newBuilder()
                    .setNumberRoot(root)
                    .build());
        } else {
            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription("The number sent is not positive")
                            .augmentDescription("[number sent: " + number + "]")
                            .asRuntimeException()
            );
        }
    }
}
