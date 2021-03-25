package com.tenetmind.greeting.server;

import com.tenetmind.helper.Helper;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GreetingServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50051)
                .addService(new GreetServiceImpl())
                .build();
        Helper.runServer(server);
    }

}
