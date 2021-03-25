package com.tenetmind.helper;

import io.grpc.Server;

import java.io.IOException;

public class Helper {

    public static void runServer(Server server) throws IOException, InterruptedException {
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
