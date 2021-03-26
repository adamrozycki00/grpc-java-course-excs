package com.tenetmind.helper;

import com.tenetmind.greeting.server.GreetServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class Helper {

    public void runServer(int port) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(port)
                .addService(new GreetServiceImpl())
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
