package microservices.book.multiplication.services;

import java.util.List;
import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;

public interface MultiplicationService {

  /**
   * Creates a {@link Multiplication} object with two randomly-generated factors between 11 and 99
   *
   * @return a multiplication object with random factors
   */
  Multiplication createRandomMultiplication();

  /**
   * @param resultAttempt
   * @return MultiplicationResultAttempt that will hold the results of the attempt and the user
   * info.
   */
  MultiplicationResultAttempt checkAttempt(final MultiplicationResultAttempt resultAttempt);

  /**
   * @param alias alias of the user
   * @return a list of the last 5 attempt of the user
   */
  List<MultiplicationResultAttempt> getStatsForUser(String alias);
}
