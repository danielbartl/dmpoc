package dev.jbaby.dmpoc.core;

import com.mongodb.WriteConcern;
import dev.jbaby.dmpoc.source.Customer;
import dev.jbaby.dmpoc.source.CustomerJpaRepository;
import dev.jbaby.dmpoc.target.CustomerDocument;
import dev.jbaby.dmpoc.target.CustomerMongoRepository;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.context.JobDashboardProgressBar;
import org.jobrunr.jobs.context.JobRunrDashboardLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class DataMigrationService {

    private static final Logger log = LoggerFactory.getLogger(DataMigrationService.class);

    private final CustomerJpaRepository jpaRepository;
    private final CustomerMongoRepository mongoRepository;
    private final MongoDatabaseFactory mongoDatabaseFactory;


    public DataMigrationService(CustomerJpaRepository jpaRepository, CustomerMongoRepository mongoRepository, MongoDatabaseFactory mongoDatabaseFactory) {
        this.jpaRepository = jpaRepository;
        this.mongoRepository = mongoRepository;
        this.mongoDatabaseFactory = mongoDatabaseFactory;
    }

    @Job(name = "Customer Data Migration", retries = 2)
    @Transactional(readOnly = true, transactionManager = "transactionManager")
    public void migrateCustomers(JobContext jobContext) {

        final Instant start = Instant.now();

        final Logger dashboardLogger = new JobRunrDashboardLogger(log);

        JobDashboardProgressBar progressBar = jobContext.progressBar(100);

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDatabaseFactory);
        // set UNACKNOWLEDGED write concern for speed
        mongoTemplate.setWriteConcern(WriteConcern.UNACKNOWLEDGED);

        dashboardLogger.info("Starting customer data migration from PostgreSQL to MongoDB...");
        mongoRepository.deleteAll(); // Clear existing data before migration

        final long totalCustomers = jpaRepository.count();
        if (totalCustomers == 0) {
            dashboardLogger.info("No customers found to migrate.");
            return;
        }

        dashboardLogger.info("Found {} total customers to migrate.", totalCustomers);
        progressBar.setProgress(0); // Start with 0% progress

        final int batchSize = 10_000 ;
        List<CustomerDocument> batch = new ArrayList<>(batchSize);
        long count = 0;

        try (Stream<Customer> customerStream = jpaRepository.streamAll()) {
            for (Customer customer : (Iterable<Customer>) customerStream::iterator) {
                batch.add(mapToDocument(customer));
                count++;
                if (batch.size() == batchSize) {
                    // Use the specialized template for a high-speed, fire-and-forget batch insert
                    mongoTemplate.insertAll(batch);
                    //mongoRepository.saveAll(batch);
                    batch.clear();
                    dashboardLogger.info("Migrated {} customers...", count);
                    progressBar.setProgress((int) ((count * 100) / totalCustomers)); // Update progress bar
                }
            }

            if (!batch.isEmpty()) {
                //mongoRepository.saveAll(batch);
                mongoTemplate.insertAll(batch);
                dashboardLogger.info("Migrated final batch of {} customers.", batch.size());
            }
        }

        log.info("Successfully migrated {} customers to MongoDB.", count);
        progressBar.setProgress(100); // Set to 100% on completion
        final Duration duration = Duration.between(start, Instant.now());
        dashboardLogger.info(
                "Migration complete. Migrated a total of {} customers in {} seconds.",
                count,
                duration.toSeconds()
        );

    }

    private CustomerDocument mapToDocument(Customer customer) {
        CustomerDocument doc = new CustomerDocument();
        doc.setId(customer.getId());
        doc.setName(customer.getName());
        doc.setEmail(customer.getEmail());
        doc.setPhone(customer.getPhone());
        doc.setStreet(customer.getStreet());
        doc.setCity(customer.getCity());
        doc.setState(customer.getState());
        doc.setCountry(customer.getCountry());
        doc.setZip(customer.getZip());
        doc.setNotes(customer.getNotes());
        return doc;
    }
}