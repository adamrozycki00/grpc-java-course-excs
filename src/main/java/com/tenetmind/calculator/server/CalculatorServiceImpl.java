package com.tenetmind.calculator.server;

import com.tenetmind.calculator.CalculatorServiceGrpc;
import com.tenetmind.calculator.SummationRequest;
import com.tenetmind.calculator.SummationResponse;
import io.grpc.stub.StreamObserver;

public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {

    @Override
    public void sum(SummationRequest request, StreamObserver<SummationResponse> responseObserver) {
        int num1 = request.getSummation().getNum1();
        int num2 = request.getSummation().getNum2();

        int result = num1 + num2;

        SummationResponse response = SummationResponse.newBuilder()
                .setResult(result)
                .build();

        responseObserver.onNext(response);

        responseObserver.onCompleted();
    }

}
