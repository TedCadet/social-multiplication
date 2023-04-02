package microservices.book.multiplication.repositories;

import java.util.Optional;
import microservices.book.multiplication.domain.Multiplication;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MultiplicationRepository extends MongoRepository<Multiplication, String> {

  Optional<Multiplication> findByFactorAAndFactorB(int factorA, int factorB);
}
