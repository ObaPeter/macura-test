package com.peter.BigQuery.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.*;
import com.peter.BigQuery.domain.YellowCabResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BigQueryService {

    // private final Credentials credential;

    public void CallSQL() throws InterruptedException, IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("classpath:auth-key.json"))
                .createScoped("https://www.googleapis.com/auth/bigquery");

        System.out.println(credentials);

        BigQuery bigquery = BigQueryOptions.newBuilder().setProjectId("bigquery-test-318614").setCredentials(credentials).build().getService();
        //BigQueryOptions.getDefaultInstance().getService();

        String query = "SELECT * FROM `bigquery-test-318614.YellowCab1.yellowcabtest` LIMIT 10";

        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();

        TableId tId = TableId.of("YellowCab1", "yellowcabtest");
        Table dataset = bigquery.getTable(tId);
// Print the results.
        log.info("Shalom says: {}", dataset.getLabels());

        List<YellowCabResponse> response = new ArrayList<>();
        for (FieldValueList row : bigquery.query(queryConfig).iterateAll()) {
            log.info("Shalom says: A");

            response.add(YellowCabResponse.builder()
                    .numberOfCabsRequired(Integer.parseInt(row.get(0).getStringValue()))
                    .numberOfTrips(Integer.parseInt(row.get(3).getStringValue()))
                    .passengerCount(Integer.parseInt(row.get(3).getStringValue()))
                    .totalTimeInMinutes(Integer.parseInt(row.get(11).getStringValue()))
                    .build());
            for (FieldValue val : row) {

                log.info("Shalom says: {},", val.toString());
            }
        }
        log.info("Shalom says: {},", response);

    }
}
