package microservices.book.multiplication.exception;

public class ResultAttemptReceivedTrueException extends IllegalArgumentException {

  public ResultAttemptReceivedTrueException() {
    super("You can't send an attempt marked as correct!!");
  }
}
