package microservices.book.multiplication.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.repositories.MultiplicationRepository;
import microservices.book.multiplication.repositories.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Given a multiplication service")
class MultiplicationServiceTest {

  @MockBean
  private RandomGeneratorService randomGeneratorService;
  @MockBean
  private MultiplicationResultAttemptRepository multiplicationResultAttemptRepository;
  @MockBean
  private UserRepository userRepository;
  @MockBean
  MultiplicationRepository multiplicationRepository;

  private MultiplicationService multiplicationService;

  private Multiplication multiplication;

  @BeforeEach
  @DisplayName("Setting up before the tests")
  public void setUp() {
    multiplicationService = new MultiplicationServiceImpl(randomGeneratorService,
        multiplicationResultAttemptRepository, userRepository, multiplicationRepository);

  }

  @ParameterizedTest
  @DisplayName("Then assert that factor A and factor b is equals to 30, 50 and the result is equals to 1500")
  @CsvSource({
      "50,30,1500"
  })
  void createRandomMultiplicationTest(int factorA, int factorB, int result) {
    // given our mocked Random Generator service will return first 50, then 30
    given(randomGeneratorService.generateRandomFactor()).willReturn(factorA, factorB);
    multiplication = multiplicationService.createRandomMultiplication();

    verify(multiplicationRepository, times(1))
        .findByFactorAAndFactorB(anyInt(), anyInt());

    assertAll(
        () -> assertEquals(factorA, multiplication.getFactorA()),
        () -> assertEquals(factorB, multiplication.getFactorB()),
        () -> assertEquals(result, multiplication.getResult())
    );
  }

  @ParameterizedTest
  @DisplayName("Then assert that checkAttempt return true or false when..")
  @CsvSource({
      "50,60,3000,true",
      "50,60,3100,false"
  })
  void checkAttemptTest(int factorA, int factorB, int result, boolean expected) {
    Multiplication multi = new Multiplication(factorA, factorB);
    User testUser = new User("id", "john");
    MultiplicationResultAttempt multiAttempt = new MultiplicationResultAttempt("id", testUser,
        multi,
        result, false);

    given(userRepository.findByAlias(anyString())).willReturn(Optional.empty());
    MultiplicationResultAttempt checkAttempt = multiplicationService.checkAttempt(multiAttempt);

    assertEquals(expected, checkAttempt.correct());
    verify(multiplicationResultAttemptRepository).save(multiAttempt);
  }

  @Test
  @DisplayName("then assert that the service can retrieve stats")
  void retrieveStatsTest() {
    Multiplication multiplication = new Multiplication(50, 60);
    User user = new User("idUser", "john_doe");
    MultiplicationResultAttempt attempt1 = new MultiplicationResultAttempt("idAttempt1", user,
        multiplication, 3010, false);
    MultiplicationResultAttempt attempt2 = new MultiplicationResultAttempt("idAttempt2", user,
        multiplication, 3051, false);
    List<MultiplicationResultAttempt> latestsAttempts = List.of(attempt1, attempt2);

    given(multiplicationResultAttemptRepository.findTopByUserAliasOrderByIdDesc(anyString()))
        .willReturn(latestsAttempts);

    List<MultiplicationResultAttempt> latestAttemptsResult = multiplicationService
        .getStatsForUser("john_doe");

    assertEquals(latestAttemptsResult, latestsAttempts);
  }
}
