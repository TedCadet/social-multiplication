package microservices.book.multiplication.dtos;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.User;

public record MultiplicationResultAttemptDto(String id, User user, Multiplication multiplication,
                                             int resultAttempt, boolean correct) {

}
