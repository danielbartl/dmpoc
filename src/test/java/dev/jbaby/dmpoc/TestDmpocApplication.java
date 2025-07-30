package dev.jbaby.dmpoc;

import org.springframework.boot.SpringApplication;

public class TestDmpocApplication {

    public static void main(String[] args) {
        SpringApplication.from(DmpocApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
