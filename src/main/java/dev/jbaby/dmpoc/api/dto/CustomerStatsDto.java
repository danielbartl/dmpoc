package dev.jbaby.dmpoc.api.dto;

import dev.jbaby.dmpoc.source.Customer;

import java.util.List;

public record CustomerStatsDto(long totalCustomers, List<Customer> customers) {
}