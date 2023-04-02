package microservices.book.multiplication.repositories;

import java.util.Optional;
import microservices.book.multiplication.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

  Optional<User> findByAlias(String alias);
}
