package com.peter.BigQuery.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class YellowCabResponse {
    private int passengerCount;
    private int numberOfTrips;
    private int totalTimeInMinutes;
    private int numberOfCabsRequired;
}
