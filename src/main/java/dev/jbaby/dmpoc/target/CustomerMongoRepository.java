package dev.jbaby.dmpoc.target;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerMongoRepository extends MongoRepository<CustomerDocument, Long> {

    List<CustomerDocument> findFirst10ByOrderByIdAsc();

}