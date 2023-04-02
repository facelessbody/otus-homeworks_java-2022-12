package ru.otus.handler;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.ProcessorException;
import ru.otus.processor.ProcessorThrowWhenSecondIsEven;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ThrowingOnEvenSecondsProcessorTest {


    @Test
    void shouldThrowExceptionOnEvenSecond() {
        var fixedClock = Clock.fixed(Instant.ofEpochSecond(100L), ZoneId.systemDefault());
        var throwingProcessor = new ProcessorThrowWhenSecondIsEven(fixedClock);

        var message = Message.builder(1L).build();

        assertThatThrownBy(() -> throwingProcessor.process(message))
                .isInstanceOf(ProcessorException.class)
                .hasMessage(ProcessorException.Messages.CANNOT_PROCESS_ON_EVEN_SECONDS);
    }
}
