package microservices.book.multiplication.events;

public record MultiplicationSolvedEvent(String multiplicationResultAttemptId,
                                        String userId,
                                        boolean correct) {

}
