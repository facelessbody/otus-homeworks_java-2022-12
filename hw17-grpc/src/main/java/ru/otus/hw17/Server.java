package ru.otus.hw17;

import io.grpc.ServerBuilder;
import ru.otus.hw17.service.NumbersCounterService;

public class Server {
    private static final int SERVER_PORT = 9000;

    public static void main(String[] args) throws Exception {
        var server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(new NumbersCounterService())
                .build();
        server.start();
        System.out.println("server waiting for client connections...");
        server.awaitTermination();
    }
}
