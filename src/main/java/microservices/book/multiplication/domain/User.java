package microservices.book.multiplication.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record User(String id, String alias) {

}
