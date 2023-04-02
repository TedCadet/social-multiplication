package microservices.book.multiplication.domain;

import org.springframework.data.mongodb.core.mapping.Document;

//@Data
//@RequiredArgsConstructor
@Document
public record MultiplicationResultAttempt(String id, User user, Multiplication multiplication,
                                          int resultAttempt, boolean correct) {

}
