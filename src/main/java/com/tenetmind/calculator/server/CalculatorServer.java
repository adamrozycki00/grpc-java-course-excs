package com.tenetmind.calculator.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class CalculatorServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50050)
                .addService(new CalculatorServiceImpl())
                .build();

        System.out.println("Starting the server...");
        server.start();
        System.out.println("The server has started");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Received shutdown request. Shutting down the server...");
            server.shutdown();
            System.out.println("Successfully stopped the server");
        }));

        server.awaitTermination();
    }

}
