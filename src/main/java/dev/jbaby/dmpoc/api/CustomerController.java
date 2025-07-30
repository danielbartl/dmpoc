package dev.jbaby.dmpoc.api;

import dev.jbaby.dmpoc.core.CustomerQueryService;
import dev.jbaby.dmpoc.api.dto.CustomerDocumentStatsDto;
import dev.jbaby.dmpoc.api.dto.CustomerStatsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer Data", description = "Endpoints for querying customer data and statistics.")
class CustomerController {

    private final CustomerQueryService customerQueryService;

    public CustomerController(CustomerQueryService customerQueryService) {
        this.customerQueryService = customerQueryService;
    }

    @GetMapping("/stats")
    @Operation(summary = "Get Customer Statistics from PostgreSQL",
            description = "Retrieves a count of customers from the PostgreSQL database.")
    @ApiResponse(responseCode = "200",
            description = "Successfully retrieved customer count.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomerStatsDto.class)))
    public ResponseEntity<CustomerStatsDto> getCustomerStats() {
        return ResponseEntity.ok(customerQueryService.getCustomerStats());
    }

    @GetMapping("/mongo/stats")
    @Operation(summary = "Get Customer Statistics from MongoDB",
            description = "Retrieves a count of customers from the MongoDB database.")
    @ApiResponse(responseCode = "200",
            description = "Successfully retrieved customer count.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomerStatsDto.class)))
    public ResponseEntity<CustomerDocumentStatsDto> getCustomerDocumentStats() {
        return ResponseEntity.ok(customerQueryService.getCustomerDocumentStats());
    }

}