package microservices.book.multiplication.dispatchers;

import microservices.book.multiplication.events.MultiplicationSolvedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
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
    kafkaTemplate.send(topic, multiplicationSolvedEvent);
  }
}
