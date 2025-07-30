package dev.jbaby.dmpoc.core;

import dev.jbaby.dmpoc.api.dto.CustomerDocumentStatsDto;
import dev.jbaby.dmpoc.api.dto.CustomerStatsDto;
import dev.jbaby.dmpoc.source.Customer;
import dev.jbaby.dmpoc.source.CustomerJpaRepository;
import dev.jbaby.dmpoc.target.CustomerDocument;
import dev.jbaby.dmpoc.target.CustomerMongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerQueryService {

    private final CustomerJpaRepository customerRepository;
    private final CustomerMongoRepository mongoRepository;

    public CustomerQueryService(CustomerJpaRepository customerRepository, CustomerMongoRepository mongoRepository) {
        this.customerRepository = customerRepository;
        this.mongoRepository = mongoRepository;
    }

    @Transactional(readOnly = true, transactionManager = "transactionManager")
    public CustomerStatsDto getCustomerStats() {
        long totalCustomers = customerRepository.count();
        List<Customer> customers = customerRepository.findFirst10ByOrderByIdAsc();
        return new CustomerStatsDto(totalCustomers, customers);
    }

    public CustomerDocumentStatsDto getCustomerDocumentStats() {
        long totalCustomers = mongoRepository.count();
        List<CustomerDocument> customers = mongoRepository.findFirst10ByOrderByIdAsc();
        return new CustomerDocumentStatsDto(totalCustomers, customers);
    }
}