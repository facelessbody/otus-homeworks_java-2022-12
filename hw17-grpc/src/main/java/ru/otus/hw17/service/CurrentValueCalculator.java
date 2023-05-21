package ru.otus.hw17.service;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otus.hw17.protobuf.generated.NumbersCounterGrpc;
import ru.otus.hw17.protobuf.generated.NumbersRange;
import ru.otus.hw17.utils.GrpcUtils;

@Slf4j
@RequiredArgsConstructor
public class CurrentValueCalculator {
    private final NumbersCounterGrpc.NumbersCounterStub remote;

    public void calculate() {
        var currentValue = new AtomicInteger(0);

        var firstValue = 0;
        var lastValue = 30;

        remote.count(
                NumbersRange.newBuilder()
                        .setFirstValue(firstValue)
                        .setLastValue(lastValue)
                        .build(),
                GrpcUtils.streamObserver(v -> {
                    log.info("received {}", v.getValue());
                    currentValue.addAndGet(v.getValue());
                })
        );

        for (int i = 0; i < 50; ++i) {
            log.info("currentValue: {}", currentValue.addAndGet(1));
            try {
                Thread.sleep(Duration.ofSeconds(1));
            } catch (InterruptedException ignored) {
            }
        }
    }
}
