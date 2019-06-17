package com.marviq.assesment.test.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class OeeService {

    @Autowired
    private ProductionService productionService;
    @Autowired
    private RuntimeService runtimeService;

    public float calculatePerformance(String machineName) {
        int normGrossProductionPer24h = 720000;
        LocalDateTime reportDateFrom = LocalDateTime.of(2018, 1, 7, 0, 0, 0);
        LocalDateTime reportDateTo = LocalDateTime.of(2018, 1, 8, 1, 0, 0);
        int actualProduction = productionService.getVariableValue(machineName, "PRODUCTION", reportDateFrom, reportDateTo);
        float performancePercentage = (float)actualProduction/normGrossProductionPer24h;
        return performancePercentage;
    }

    public float calculateAvailability(String machineName) {
        int normUptime = 960;
        int actualUptime = runtimeService.getUpTimeInMinutes(machineName);
        float availabilityPercentage = (float) actualUptime / normUptime;
        return availabilityPercentage;
    }

    public float calculateQuality(String machineName) {
        LocalDateTime reportDateFrom = LocalDateTime.of(2018, 1, 7, 0, 0, 0);
        LocalDateTime reportDateTo = LocalDateTime.of(2018, 1, 8, 1, 0, 0);
        int actualGrossProduction = productionService.getVariableValue(machineName, "PRODUCTION", reportDateFrom, reportDateTo);
        int actualScrapProduction = productionService.getVariableValue(machineName, "SCRAP", reportDateFrom, reportDateTo);
        float qualityPercentage = (float) (actualGrossProduction - actualScrapProduction )/ actualGrossProduction ;
        return qualityPercentage;
    }

    //C
    public float calculateOEE(String machineName) {
        float quality = calculateQuality(machineName);
        float availability = calculateAvailability(machineName);
        float performance = calculatePerformance(machineName);
        return (quality * availability * performance) * 100 ;
    }

}

