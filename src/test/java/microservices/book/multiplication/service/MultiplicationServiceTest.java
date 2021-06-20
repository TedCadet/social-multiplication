package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Given a multiplication service")
public class MultiplicationServiceTest {
    //int rand;
    Multiplication multiplication;

    @MockBean
    private RandomGeneratorService randomGeneratorService;

    @Autowired
    private MultiplicationService multiplicationService;

    @BeforeEach
    @DisplayName("When multiplication service creates a random multiplication")
    public void givenRGS() {
        //rand = randomGeneratorService.generateRandomFactor();
        multiplication = multiplicationService.createRandomMultiplication();
    }

    @ParameterizedTest
    @DisplayName("Then assert that factor A and factor b is equals to 30, 50 and the result is equals to 1500")
    @CsvSource({
            "50,30,1500"
    })
    public void createRandomMultiplicationTest(int factorA, int factorB, int result) {
        // given our mocked Random Generator service will return first 50, then 30
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

        assertAll(
                () -> assertEquals(multiplication.getFactorA(), factorA),
                () -> assertEquals(multiplication.getFactorB(), factorB),
                () -> assertEquals(multiplication.getResult(), result)
        );
    }
}
