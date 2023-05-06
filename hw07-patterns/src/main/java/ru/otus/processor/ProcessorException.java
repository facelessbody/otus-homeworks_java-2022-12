package ru.otus.processor;

public class ProcessorException extends RuntimeException {

    public ProcessorException(final String message) {
        this(message, null);
    }

    public ProcessorException(final String message, final Throwable cause) {
        super(message);
        if (cause != null) {
            super.initCause(cause);
        }
    }

    public static final class Messages {
        public static final String CANNOT_PROCESS_ON_EVEN_SECONDS = "cannot process messages on even seconds";

        private Messages() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }
}
