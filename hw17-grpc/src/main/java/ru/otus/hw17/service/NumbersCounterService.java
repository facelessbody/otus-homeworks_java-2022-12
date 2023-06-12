package ru.otus.hw17.service;

import java.time.Duration;
import java.util.stream.IntStream;

import io.grpc.stub.StreamObserver;
import ru.otus.hw17.protobuf.generated.Number;
import ru.otus.hw17.protobuf.generated.NumbersCounterGrpc;
import ru.otus.hw17.protobuf.generated.NumbersRange;

public class NumbersCounterService extends NumbersCounterGrpc.NumbersCounterImplBase {
    @Override
    public void count(NumbersRange request, StreamObserver<Number> responseObserver) {
        IntStream.rangeClosed(request.getFirstValue() + 1, request.getLastValue())
                .mapToObj(this::toGrpc)
                .forEach(x -> {
                    responseObserver.onNext(x);
                    try {
                        Thread.sleep(Duration.ofSeconds(2));
                    } catch (InterruptedException ignored) {
                    }
                });
        responseObserver.onCompleted();
    }

    private Number toGrpc(int i) {
        return Number.newBuilder().setValue(i).build();
    }
}
