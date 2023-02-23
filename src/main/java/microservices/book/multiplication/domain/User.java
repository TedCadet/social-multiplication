package microservices.book.multiplication.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
public final class User {
  private final String alias;
}
