package ru.otus.processor;

import lombok.experimental.StandardException;
import lombok.experimental.UtilityClass;

@StandardException
public class ProcessorException extends RuntimeException {

    @UtilityClass
    public static class Messages {
        public static String CANNOT_PROCESS_ON_EVEN_SECONDS = "cannot process messages on even seconds";
    }
}
