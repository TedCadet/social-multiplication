package microservices.book.multiplication.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Predicate;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Given a Random Generator Service")
class RandomGeneratorServiceTest {

  private RandomGeneratorService randomGeneratorService;
  private final Predicate<Integer> isBetweenElevenAndOneHundred = i -> i >= 11 && i < 100;

  @BeforeEach
  @DisplayName("Setting up before the tests")
  void setUp(){
    randomGeneratorService = new RandomGeneratorServiceImpl();
  }

  @Test
  void generateRandomFactorIsBetweenExpectedLimits() {
    /*
     when a good sample of randomly generated factors is generated
     then all of them should be between 11 and 100
     because we want a middle-complexity calculation
     */
    boolean assertThatAllElementsAreBetweenElevenAndOneHundred =
        IntStream.range(0, 1000)
          .map(i -> randomGeneratorService.generateRandomFactor())
          .allMatch(i -> isBetweenElevenAndOneHundred.test(i));

    assertTrue(assertThatAllElementsAreBetweenElevenAndOneHundred);
  }
}
