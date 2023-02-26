package microservices.book.multiplication.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import microservices.book.multiplication.domain.Multiplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Given a multiplication service")
class MultiplicationServiceTest {

  //int rand;
  Multiplication multiplication;

  @Mock
  private RandomGeneratorService randomGeneratorService;

  private MultiplicationService multiplicationService;

  @BeforeEach
  @DisplayName("Setting up before the tests")
  public void setUp() {
    multiplicationService = new MultiplicationServiceImpl(randomGeneratorService);

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

    assertAll(
        () -> assertEquals(factorA, multiplication.getFactorA()),
        () -> assertEquals(factorB, multiplication.getFactorB()),
        () -> assertEquals(result, multiplication.getResult())
    );
  }
}
