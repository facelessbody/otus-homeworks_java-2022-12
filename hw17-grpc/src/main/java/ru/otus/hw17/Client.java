package ru.otus.hw17;

import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import ru.otus.hw17.protobuf.generated.NumbersCounterGrpc;
import ru.otus.hw17.service.CurrentValueCalculator;

@Slf4j
public class Client {
    private static final String SERVER_NAME = "localhost";
    private static final int SERVER_PORT = 9000;

    public static void main(String[] args) {


        var channel = ManagedChannelBuilder.forAddress(SERVER_NAME, SERVER_PORT)
                .usePlaintext()
                .build();
        var stub = NumbersCounterGrpc.newStub(channel);

        var calc = new CurrentValueCalculator(stub);
        calc.calculate();
    }
}
