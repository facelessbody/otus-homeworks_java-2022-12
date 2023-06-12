package ru.otus.hw17.utils;

import java.util.function.Consumer;

import io.grpc.stub.StreamObserver;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GrpcUtils {
    public static <T> StreamObserver<T> streamObserver(Consumer<T> onNext) {
        return streamObserver(onNext, null, null);
    }

    public static <T> StreamObserver<T> streamObserver(Consumer<T> onNext, Consumer<Throwable> onError,
                                                       Runnable onComplete) {
        return new StreamObserver<>() {
            @Override
            public void onNext(T value) {
                if (onNext != null) {
                    onNext.accept(value);
                }
            }

            @Override
            public void onError(Throwable t) {
                if (onError != null) {
                    onError.accept(t);
                }
            }

            @Override
            public void onCompleted() {
                if (onComplete != null) {
                    onComplete.run();
                }
            }
        };
    }
}
