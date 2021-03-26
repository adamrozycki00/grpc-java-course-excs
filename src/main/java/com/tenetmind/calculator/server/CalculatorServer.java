package com.tenetmind.calculator.server;

import com.tenetmind.helper.Helper;
<<<<<<< HEAD
=======
import io.grpc.Server;
import io.grpc.ServerBuilder;
>>>>>>> origin

import java.io.IOException;

public class CalculatorServer {

    public static void main(String[] args) throws IOException, InterruptedException {
<<<<<<< HEAD
        new Helper().runServer(50050);
=======
        Server server = ServerBuilder.forPort(50050)
                .addService(new CalculatorServiceImpl())
                .build();
        Helper.runServer(server);
>>>>>>> origin
    }

}
