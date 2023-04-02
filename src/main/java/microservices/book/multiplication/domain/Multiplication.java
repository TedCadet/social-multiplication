package microservices.book.multiplication.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public final class Multiplication {

  private String id;
  private int factorA;
  private int factorB;
  private int result;

  public Multiplication(int factorA, int factorB) {
    this.factorA = factorA;
    this.factorB = factorB;
    this.result = factorA * factorB;
  }
}
