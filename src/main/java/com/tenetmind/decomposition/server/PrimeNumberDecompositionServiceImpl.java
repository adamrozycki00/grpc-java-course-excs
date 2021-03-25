package com.tenetmind.decomposition.server;

import com.tenetmind.decomposition.PrimeNumberDecompositionRequest;
import com.tenetmind.decomposition.PrimeNumberDecompositionResponse;
import com.tenetmind.decomposition.PrimeNumberDecompositionServiceGrpc;
import io.grpc.stub.StreamObserver;

public class PrimeNumberDecompositionServiceImpl
        extends PrimeNumberDecompositionServiceGrpc.PrimeNumberDecompositionServiceImplBase {

    @Override
    public void decompose(PrimeNumberDecompositionRequest request, StreamObserver<PrimeNumberDecompositionResponse> responseObserver) {
        int number = request.getNumber();
        int prime = 2;

        while (number > 1) {
            if (number % prime == 0) {
                responseObserver.onNext(PrimeNumberDecompositionResponse.newBuilder().
                        setPartialResult(prime)
                        .build());
                number /= prime;
            } else {
                prime++;
            }
        }

        responseObserver.onCompleted();
    }

}
