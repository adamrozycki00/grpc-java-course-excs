package com.tenetmind.calculator.server;

import com.tenetmind.calculator.*;
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

}
