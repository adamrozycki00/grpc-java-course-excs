package com.tenetmind.decomposition.server;

import com.tenetmind.decomposition.PrimeNumberDecompositionRequest;
import com.tenetmind.decomposition.PrimeNumberDecompositionResponse;
import com.tenetmind.decomposition.PrimeNumberDecompositionServiceGrpc;
import io.grpc.stub.StreamObserver;

public class PrimeNumberDecompositionServiceImpl
        extends PrimeNumberDecompositionServiceGrpc.PrimeNumberDecompositionServiceImplBase {

    @Override
    public void decompose(PrimeNumberDecompositionRequest request, StreamObserver<PrimeNumberDecompositionResponse> responseObserver) {
    }

}
