package com.nilesh.practice.flyway;

import jakarta.annotation.PostConstruct;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;

// @Configuration
public class FlywayConfig {

    private final Flyway flyway;

    public FlywayConfig(Flyway flyway) {
        this.flyway = flyway;
    }

    // @PostConstruct
    public void cleanDatabaseBeforeMigration() {
        // Clean the database before applying migrations
        flyway.clean();
    }
}