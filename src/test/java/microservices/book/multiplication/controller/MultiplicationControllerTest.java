package microservices.book.multiplication.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.service.MultiplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
@WebMvcTest(MultiplicationController.class)
@DisplayName("Given the multiplicationController")
class MultiplicationControllerTest {

  @MockBean
  private MultiplicationService multiplicationService;

  @Autowired
  private MockMvc mvc;

  private JacksonTester<Multiplication> json;

  @BeforeEach
  public void setup() {

    JacksonTester.initFields(this, new ObjectMapper());
  }

  @Test
  @DisplayName("assert that we can send a random multiplication")
  void getRandomMultiplicationTest() throws Exception {
    given(multiplicationService.createRandomMultiplication()).willReturn(
        new Multiplication(70, 20));

    MockHttpServletResponse response = mvc.perform(
        get("/multiplications/random")
            .accept(MediaType.APPLICATION_JSON)
    ).andReturn().getResponse();

    assertAll(
        () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
        () -> assertEquals(json.write(new Multiplication(70, 20)).getJson(),
            response.getContentAsString())
    );
  }

}
