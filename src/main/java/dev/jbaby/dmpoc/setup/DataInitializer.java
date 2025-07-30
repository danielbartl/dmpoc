package dev.jbaby.dmpoc.setup;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class DataInitializer implements CommandLineRunner {

    private final SetupSource setupSource;

    public DataInitializer(SetupSource setupSource) {
        this.setupSource = setupSource;
    }

    @Override
    public void run(String... args) {
        setupSource.setupSourceData();
    }
}