package microservices.book.multiplication.services;

import java.util.List;
import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.exception.ResultAttemptReceivedTrueException;
import microservices.book.multiplication.repositories.MultiplicationRepository;
import microservices.book.multiplication.repositories.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

  private final RandomGeneratorService randomGeneratorService;
  private final MultiplicationResultAttemptRepository multiplicationResultAttemptRepository;
  private final UserRepository userRepository;
  private final MultiplicationRepository multiplicationRepository;

  @Autowired
  public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorService,
      MultiplicationResultAttemptRepository multiplicationResultAttemptRepository,
      UserRepository userRepository, MultiplicationRepository multiplicationRepository) {
    this.randomGeneratorService = randomGeneratorService;
    this.multiplicationResultAttemptRepository = multiplicationResultAttemptRepository;
    this.userRepository = userRepository;
    this.multiplicationRepository = multiplicationRepository;
  }

  @Override
  public Multiplication createRandomMultiplication() {
    int factorA = randomGeneratorService.generateRandomFactor();
    int factorB = randomGeneratorService.generateRandomFactor();

    return multiplicationRepository.findByFactorAAndFactorB(factorA,
        factorB).orElse(new Multiplication(factorA, factorB));
  }

  @Override
  public MultiplicationResultAttempt checkAttempt(MultiplicationResultAttempt resultAttempt)
      throws ResultAttemptReceivedTrueException {
    User user = userRepository.findByAlias(resultAttempt.user().alias())
        .orElse(resultAttempt.user());

    boolean correct =
        resultAttempt.multiplication().getResult() == resultAttempt.resultAttempt();

    if (resultAttempt.correct()) {
      throw new ResultAttemptReceivedTrueException();
    }

    MultiplicationResultAttempt multiplicationResultAttempt = new MultiplicationResultAttempt(
        resultAttempt.id(),
        user,
        resultAttempt.multiplication(),
        resultAttempt.resultAttempt(),
        correct);

    multiplicationResultAttemptRepository.save(multiplicationResultAttempt);

    return multiplicationResultAttempt;
  }

  @Override
  public List<MultiplicationResultAttempt> getStatsForUser(String alias) {
    return multiplicationResultAttemptRepository.findTopByUserAliasOrderByIdDesc(alias);
  }
}
