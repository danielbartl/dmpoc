package dev.jbaby.dmpoc.api;

import dev.jbaby.dmpoc.core.CustomerQueryService;
import dev.jbaby.dmpoc.api.dto.CustomerDocumentStatsDto;
import dev.jbaby.dmpoc.api.dto.CustomerStatsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
class CustomerController {

    private final CustomerQueryService customerQueryService;

    public CustomerController(CustomerQueryService customerQueryService) {
        this.customerQueryService = customerQueryService;
    }

    @GetMapping("/stats")
    public ResponseEntity<CustomerStatsDto> getCustomerStats() {
        return ResponseEntity.ok(customerQueryService.getCustomerStats());
    }

    @GetMapping("/mongo/stats")
    public ResponseEntity<CustomerDocumentStatsDto> getCustomerDocumentStats() {
        return ResponseEntity.ok(customerQueryService.getCustomerDocumentStats());
    }

}