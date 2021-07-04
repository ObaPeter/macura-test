package com.peter.BigQuery.controllers;

import com.peter.BigQuery.domain.YellowCabResponse;
import com.peter.BigQuery.service.YellowCabService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("peter/yellow-cab")
@RequiredArgsConstructor
public class YellowCabController {
    private final YellowCabService yellowCabService;


    @GetMapping(value = "fetch-test-data")
    public ResponseEntity<List<YellowCabResponse>> generateGlobalReference() throws IOException, InterruptedException {
        List<YellowCabResponse> response = yellowCabService.getComputedData();
        return ResponseEntity.ok()
                .body(response);
    }

}
