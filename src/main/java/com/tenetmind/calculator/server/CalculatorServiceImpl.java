package com.tenetmind.calculator.server;

import com.tenetmind.calculator.CalculatorServiceGrpc;
import com.tenetmind.calculator.SummationRequest;
import com.tenetmind.calculator.SummationResponse;
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

}
