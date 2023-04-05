package microservices.book.multiplication.repositories;

import java.util.List;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MultiplicationResultAttemptRepository extends
    MongoRepository<MultiplicationResultAttempt, String> {

  List<MultiplicationResultAttempt> findTop5ByUserAliasOrderByIdDesc(String userAlias);
}

