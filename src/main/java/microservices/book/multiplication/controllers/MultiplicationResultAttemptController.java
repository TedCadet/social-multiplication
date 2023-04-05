package microservices.book.multiplication.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.dtos.MultiplicationResultAttemptDto;
import microservices.book.multiplication.services.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/results")
public class MultiplicationResultAttemptController {

  @Autowired
  private final MultiplicationService multiplicationService;

  @PostMapping
  ResponseEntity<MultiplicationResultAttempt> postResults(
      @RequestBody MultiplicationResultAttemptDto multiplicationResultAttemptDto) {
    MultiplicationResultAttempt multiplicationResultAttempt = new MultiplicationResultAttempt(
        multiplicationResultAttemptDto.id(),
        multiplicationResultAttemptDto.user(),
        multiplicationResultAttemptDto.multiplication(),
        multiplicationResultAttemptDto.resultAttempt(),
        multiplicationResultAttemptDto.correct());

    return ResponseEntity.ok(multiplicationService.checkAttempt(multiplicationResultAttempt));
  }

  @GetMapping
  ResponseEntity<List<MultiplicationResultAttempt>> getStatistics(
      @RequestParam("alias") String alias) {
    return ResponseEntity.ok(multiplicationService.getStatsForUser(alias));
  }
}
