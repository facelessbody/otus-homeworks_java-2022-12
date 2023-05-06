package ru.otus.processor;

import java.time.Clock;

import ru.otus.model.Message;

import static ru.otus.processor.ProcessorException.Messages.CANNOT_PROCESS_ON_EVEN_SECONDS;

public class ProcessorThrowWhenSecondIsEven implements Processor {
    private final Clock clock;

    public ProcessorThrowWhenSecondIsEven(Clock clock) {
        this.clock = clock;
    }

    @Override
    public Message process(Message message) {
        if (clock.instant().getEpochSecond() % 2 == 0) {
            throw new ProcessorException(CANNOT_PROCESS_ON_EVEN_SECONDS);
        }
        return message;
    }
}
