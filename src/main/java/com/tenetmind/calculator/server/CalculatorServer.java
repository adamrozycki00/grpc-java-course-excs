package com.tenetmind.calculator.server;

import com.tenetmind.greeting.server.GreetServiceImpl;
import com.tenetmind.helper.Helper;

import java.io.IOException;

public class CalculatorServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        new Helper().runServer(new CalculatorServiceImpl(), 50052);
    }

}
