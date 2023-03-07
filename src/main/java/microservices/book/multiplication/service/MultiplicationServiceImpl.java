package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.exception.ResultAttemptReceivedTrueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

  private final RandomGeneratorService randomGeneratorService;

  @Autowired
  public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorService) {
    this.randomGeneratorService = randomGeneratorService;
  }

  @Override
  public Multiplication createRandomMultiplication() {
    int factorA = randomGeneratorService.generateRandomFactor();
    int factorB = randomGeneratorService.generateRandomFactor();
    return new Multiplication(factorA, factorB);
  }

  @Override
  public MultiplicationResultAttempt checkAttempt(MultiplicationResultAttempt resultAttempt)
      throws ResultAttemptReceivedTrueException {
    boolean correct =
        resultAttempt.getMultiplication().getResult() == resultAttempt.getResultAttempt();

    if (resultAttempt.isCorrect()) {
      throw new ResultAttemptReceivedTrueException();
    }

    return new MultiplicationResultAttempt(
        resultAttempt.getUser(),
        resultAttempt.getMultiplication(),
        resultAttempt.getResultAttempt(),
        correct);
  }
}
