package microservices.book.multiplication.controller;

import lombok.RequiredArgsConstructor;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/results")
public class MultiplicationResultAttemptController {

  @Autowired
  private final MultiplicationService multiplicationService;

  protected record ResultResponse(boolean correct) {

  }

  @PostMapping
  ResponseEntity<ResultResponse> postResults(
      @RequestBody MultiplicationResultAttempt multiplicationResultAttempt) {
    return ResponseEntity.ok(
        new ResultResponse(multiplicationService.checkAttempt(multiplicationResultAttempt))
    );
  }
}
