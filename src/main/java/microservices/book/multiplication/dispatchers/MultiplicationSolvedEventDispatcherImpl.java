package microservices.book.multiplication.dispatchers;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import microservices.book.multiplication.events.MultiplicationSolvedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MultiplicationSolvedEventDispatcherImpl implements
    MultiplicationSolvedEventDispatcher {

  @Value("${kafka.topics.multiplicationSolved}")
  private String topic;
  private final KafkaTemplate<String, MultiplicationSolvedEvent> kafkaTemplate;

  @Autowired
  public MultiplicationSolvedEventDispatcherImpl(
      KafkaTemplate<String, MultiplicationSolvedEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void send(MultiplicationSolvedEvent multiplicationSolvedEvent) {
    log.info("sending this event: {}", multiplicationSolvedEvent);

    CompletableFuture
        .runAsync(() -> kafkaTemplate.send(topic, multiplicationSolvedEvent))
        .thenAccept(logSendResult)
        .exceptionally(handleException);
  }

  Consumer<Void> logSendResult = res -> log.info("MultiplicationSolvedEvent sent");

  Function<Throwable, Void> handleException = ex -> {
    log.error("error while sending a MultiplicationSolvedEvent to kafka: ", ex);
    return null;
  };
}
