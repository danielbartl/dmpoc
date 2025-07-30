package dev.jbaby.dmpoc.setup;

import dev.jbaby.dmpoc.source.Customer;
import dev.jbaby.dmpoc.source.CustomerJpaRepository;
import dev.jbaby.dmpoc.target.CustomerMongoRepository;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
class SetupSource {

    private final CustomerJpaRepository customerRepository;
    private final CustomerMongoRepository mongoRepository;
    private final Faker faker = new Faker();

    public SetupSource(CustomerJpaRepository customerRepository, CustomerMongoRepository mongoRepository) {
        this.customerRepository = customerRepository;
        this.mongoRepository = mongoRepository;
    }

    @Transactional
    public void setupSourceData() {
        log.info("Clearing existing data from MongoDB...");
        mongoRepository.deleteAll();

        if (customerRepository.count() > 0) {
            log.info("Source customer data already exists in PostgreSQL. Skipping generation.");
            return;
        }

        log.info("Generating 1,300,000 customers for PostgreSQL...");

        final int totalCustomers = 1_300_000;
        // Must match spring.jpa.properties.hibernate.jdbc.batch_size
        final int batchSize = 10000;
        List<Customer> customerBatch = new ArrayList<>(batchSize);

        for (int i = 0; i < totalCustomers; i++) {
            customerBatch.add(createRandomCustomer());

            if (customerBatch.size() == batchSize) {
                customerRepository.saveAll(customerBatch);
                customerBatch.clear();
                log.info("Inserted {}/{} customers into PostgreSQL.", i + 1, totalCustomers);
            }
        }

        if (!customerBatch.isEmpty()) {
            customerRepository.saveAll(customerBatch);
        }

        log.info("Successfully generated and inserted 1,300,000 customers into PostgreSQL.");
    }

    private Customer createRandomCustomer() {
        Customer customer = new Customer();
        customer.setName(faker.name().fullName());
        customer.setEmail(faker.internet().emailAddress());
        customer.setPhone(faker.phoneNumber().phoneNumber());
        customer.setStreet(faker.address().streetAddress());
        customer.setCity(faker.address().city());
        customer.setState(faker.address().stateAbbr());
        customer.setCountry(faker.address().country());
        customer.setZip(faker.address().zipCode());
        customer.setNotes(faker.lorem().sentence());
        return customer;
    }
}