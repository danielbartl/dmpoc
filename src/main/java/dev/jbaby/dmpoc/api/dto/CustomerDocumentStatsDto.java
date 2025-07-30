package dev.jbaby.dmpoc.api.dto;

import dev.jbaby.dmpoc.target.CustomerDocument;

import java.util.List;

public record CustomerDocumentStatsDto(long totalCustomers, List<CustomerDocument> customers) {
}