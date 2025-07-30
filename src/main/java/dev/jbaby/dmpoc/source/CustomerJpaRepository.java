package dev.jbaby.dmpoc.source;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface CustomerJpaRepository extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c")
    Stream<Customer> streamAll();

    List<Customer> findFirst10ByOrderByIdAsc();

}