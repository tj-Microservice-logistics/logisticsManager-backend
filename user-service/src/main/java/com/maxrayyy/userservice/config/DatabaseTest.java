package com.maxrayyy.userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseTest implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Testing database connection...");
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        System.out.println("Database connection successful: " + result);
    }
}
