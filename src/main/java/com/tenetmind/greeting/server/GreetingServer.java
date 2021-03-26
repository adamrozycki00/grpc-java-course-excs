package com.tenetmind.greeting.server;

import com.tenetmind.helper.Helper;

import java.io.IOException;

public class GreetingServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        new Helper().runServer(50051);
    }

}
