package com.tenetmind.calculator.server;

import com.tenetmind.calculator.*;
import io.grpc.stub.StreamObserver;

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

}
