package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;

public interface MultiplicationService {
/**
 * Creates a {@link Multiplication} object with two randomly-generated factors between 11 and 99
 * @return a multiplication object with random factors
 */
    Multiplication createRandomMultiplication();

    /**
     *
     * @param resultAttempt
     * @return MultiplicationResultAttempt that will hold the results of the attempt and the user info.
     */
    MultiplicationResultAttempt checkAttempt(final MultiplicationResultAttempt resultAttempt);
}
