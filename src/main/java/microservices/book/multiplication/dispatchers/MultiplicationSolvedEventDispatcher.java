package microservices.book.multiplication.dispatchers;

import microservices.book.multiplication.events.MultiplicationSolvedEvent;

public interface MultiplicationSolvedEventDispatcher {

  void send(final MultiplicationSolvedEvent multiplicationSolvedEvent);
}
