package microservices.book.multiplication.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public final class MultiplicationResultAttempt {

  private final User user;
  private final Multiplication multiplication;
  private final int resultAttempt;

  MultiplicationResultAttempt() {
    user = null;
    multiplication = null;
    resultAttempt = -1;
  }
}
