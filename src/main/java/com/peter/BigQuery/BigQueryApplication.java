package com.peter.BigQuery;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class BigQueryApplication implements CommandLineRunner {
    private final com.peter.BigQuery.service.YellowCabService bigQueryService;

    public static void main(String[] args) {
        SpringApplication.run(BigQueryApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        bigQueryService.getComputedData();

    }
}
