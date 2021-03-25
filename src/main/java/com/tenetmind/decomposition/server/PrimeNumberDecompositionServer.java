package com.tenetmind.decomposition.server;

import com.tenetmind.helper.Helper;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class PrimeNumberDecompositionServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50052)
                .addService(new PrimeNumberDecompositionServiceImpl())
                .build();
        Helper.runServer(server);
    }

}


