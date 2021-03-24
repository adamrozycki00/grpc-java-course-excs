package com.tenetmind.calculator.client;

import com.tenetmind.calculator.CalculatorServiceGrpc;
import com.tenetmind.calculator.Summation;
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

        int num1 = 10;
        int num2 = 3;

        Summation summation = Summation.newBuilder()
                .setNum1(num1)
                .setNum2(num2)
                .build();

        SummationRequest request = SummationRequest.newBuilder()
                .setSummation(summation)
                .build();

        SummationResponse sum = calcClient.sum(request);

        System.out.println("The sum of " + num1 + " and " + num2 + " is " + sum.getResult());
    }

}
