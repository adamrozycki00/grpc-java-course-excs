package com.tenetmind.calculator.client;

import com.tenetmind.calculator.CalculatorServiceGrpc;
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

        CalculatorServiceGrpc.CalculatorServiceBlockingStub calcClient = newBlockingStub(channel);

        SummationRequest request = SummationRequest.newBuilder()
                .setNum1(10)
                .setNum2(3)
                .build();

        SummationResponse sum = calcClient.sum(request);

        System.out.println("The sum of " + request.getNum1() + " and " + request.getNum2() +
                " is " + sum.getResult());
    }

}
