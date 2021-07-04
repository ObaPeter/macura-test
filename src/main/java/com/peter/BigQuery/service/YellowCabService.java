package com.peter.BigQuery.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.peter.BigQuery.domain.YellowCabResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class YellowCabService {

    @Value("${app.big-query.project-id}")
    String projectId;

    @Value("${app.big-query.query}")
    String query;


    public List<YellowCabResponse> getComputedData() throws IOException, InterruptedException {

        // File keyStore = new ResourceUtils.getFile("classpath:auth-key.json");



        GoogleCredentials credentials = GoogleCredentials.fromStream(new ClassPathResource("auth-key.json").getInputStream())
                .createScoped("https://www.googleapis.com/auth/bigquery");

        BigQuery bigquery = BigQueryOptions.newBuilder().setProjectId(projectId).setCredentials(credentials).build().getService();

        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();

        List<YellowCabResponse> response = new ArrayList<>();
        for (FieldValueList row : bigquery.query(queryConfig).iterateAll()) {
            response.add(YellowCabResponse.builder()
                    .numberOfCabsRequired(((int) Double.parseDouble(row.get(3).getStringValue())))
                    .numberOfTrips(Integer.parseInt(row.get(1).getStringValue()))
                    .passengerCount(Integer.parseInt(row.get(0).getStringValue()))
                    .totalTimeInMinutes(Integer.parseInt(row.get(2).getStringValue()))
                    .build());
        }
        log.info("Completed processing for {} items", response.size());
        return response;
    }
}
