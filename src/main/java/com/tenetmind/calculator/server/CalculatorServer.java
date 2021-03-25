package com.tenetmind.calculator.server;

import com.tenetmind.helper.Helper;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class CalculatorServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50050)
                .addService(new CalculatorServiceImpl())
                .build();
        Helper.runServer(server);
    }

}
