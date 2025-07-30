package dev.jbaby.dmpoc.api;

import dev.jbaby.dmpoc.core.DataMigrationService;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/migrate")
class MigrationController {

    private final JobScheduler jobScheduler;

    public MigrationController(JobScheduler jobScheduler) {
        this.jobScheduler = jobScheduler;
    }

    @PostMapping("/customers")
    public ResponseEntity<String> migrateCustomers() {
        jobScheduler.<DataMigrationService>enqueue(dms -> dms.migrateCustomers(null));
        return ResponseEntity.accepted().body("Customer migration job has been started.");
    }
}