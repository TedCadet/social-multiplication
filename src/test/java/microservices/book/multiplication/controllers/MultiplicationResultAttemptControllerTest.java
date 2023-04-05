package microservices.book.multiplication.controllers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.services.MultiplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MultiplicationResultAttemptController.class)
@DisplayName("Given the MultiplicationResultAttemptController")
class MultiplicationResultAttemptControllerTest {

  @MockBean
  private MultiplicationService multiplicationService;

  @Autowired
  private MockMvc mvc;

  private JacksonTester<MultiplicationResultAttempt> jsonResult;
  private JacksonTester<MultiplicationResultAttempt> jsonResponse;
  private JacksonTester<MultiplicationResultAttempt> jsonResultAttempt;
  private JacksonTester<List<MultiplicationResultAttempt>> jsonResultAttemptList;

  @BeforeEach
  public void setup() {
    JacksonTester.initFields(this, new ObjectMapper());
  }

  @ParameterizedTest
  @CsvSource({
      "true",
      "false"
  })
  @DisplayName("assert the /result endpoint")
  void postResultReturnTest(boolean correct) throws Exception {
    int factorA = 50;
    int factorB = 70;
    int attemptResult = 3500;
    User testUser = new User("id", "john");
    Multiplication multi = new Multiplication(factorA, factorB);
    MultiplicationResultAttempt attempt = new MultiplicationResultAttempt("id", testUser, multi,
        attemptResult, false);
    MultiplicationResultAttempt resultAttemptExpected = new MultiplicationResultAttempt("id",
        testUser,
        multi,
        attemptResult, correct);

    given(multiplicationService.checkAttempt(any(MultiplicationResultAttempt.class))).willReturn(
        resultAttemptExpected);

    MockHttpServletResponse response = mvc.perform(
            post("/results")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonResult.write(attempt).getJson()))
        .andExpect(status().isOk())
        .andReturn().getResponse();

    assertEquals(jsonResponse.write(resultAttemptExpected).getJson(),
        response.getContentAsString());
  }

  @Test
  @DisplayName("assert the getUserStats endpoint")
  void getUserStats() throws Exception {
    String alias = "john_doe";
    User user = new User("idUser", alias);
    Multiplication multiplication = new Multiplication(50, 70);
    MultiplicationResultAttempt attempt = new MultiplicationResultAttempt("idAttempt",
        user, multiplication, 3500, true);

    List<MultiplicationResultAttempt> recentAttempts = List.of(attempt, attempt);

    given(multiplicationService.getStatsForUser(anyString())).willReturn(recentAttempts);

    MockHttpServletResponse response =
        mvc.perform(get("/results")
                .param("alias", alias))
            .andReturn().getResponse();

    assertAll(
        () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
        () -> assertEquals(response.getContentAsString(),
            jsonResultAttemptList.write(recentAttempts).getJson())
    );
  }
}
