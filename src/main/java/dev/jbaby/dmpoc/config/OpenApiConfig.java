package dev.jbaby.dmpoc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Data Migration API")
                        .version("1.0.0")
                        .description("""
                                This API provides endpoints to trigger and manage a data migration job
                                that transfers customer data from a PostgreSQL database to MongoDB.
                                It uses JobRunr for background processing.
                                """));
    }
}