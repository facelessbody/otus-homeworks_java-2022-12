package ru.otus.processor;

import java.time.Clock;

import lombok.RequiredArgsConstructor;
import ru.otus.model.Message;

import static ru.otus.processor.ProcessorException.Messages.CANNOT_PROCESS_ON_EVEN_SECONDS;

@RequiredArgsConstructor
public class ProcessorThrowWhenSecondIsEven implements Processor {
    private final Clock clock;

    @Override
    public Message process(Message message) {
        if (clock.instant().getEpochSecond() % 2 == 0) {
            throw new ProcessorException(CANNOT_PROCESS_ON_EVEN_SECONDS);
        }
        return message;
    }
}
