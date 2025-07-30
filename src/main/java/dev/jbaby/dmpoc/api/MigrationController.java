package dev.jbaby.dmpoc.api;

import dev.jbaby.dmpoc.core.DataMigrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/migrate")
@Tag(name = "Data Migration", description = "Endpoints for triggering data migration jobs.")
class MigrationController {

    private final JobScheduler jobScheduler;

    public MigrationController(JobScheduler jobScheduler) {
        this.jobScheduler = jobScheduler;
    }

    @PostMapping("/customers")
    @Operation(summary = "Start Customer Data Migration",
            description = "Enqueues a background job to migrate customer data from PostgreSQL to MongoDB. " +
                    "The job progress can be monitored via the JobRunr dashboard.")
    @ApiResponse(responseCode = "200", description = "Migration job successfully enqueued.")
    public ResponseEntity<String> migrateCustomers() {
        jobScheduler.<DataMigrationService>enqueue(dms -> dms.migrateCustomers(null));
        return ResponseEntity.accepted().body("Customer migration job has been started.");
    }
}