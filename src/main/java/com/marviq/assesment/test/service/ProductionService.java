package com.marviq.assesment.test.service;

import com.marviq.assesment.test.model.Production;
import com.marviq.assesment.test.repository.ProductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class ProductionService {

    @Autowired
    private ProductionRepository productionRepository;


    public int getVariableValue(String machineName, String requestedValue, LocalDateTime reportDateFrom, LocalDateTime reportDateTo) {
        // summing up the production/scrap values for the given date/time
        int productionValue = 0;
        List<Production> productionList = productionRepository.findAllByMachineName(machineName);
        for (Production production : productionList) {
            if (production.getVariableName().equals(requestedValue)){
                if(production.getDatetimeFrom().isAfter(reportDateFrom)){
                    if (production.getDatetimeTo().isBefore(reportDateTo)){
                        productionValue += production.getValue();
                    }
                }
            }
        }
        return productionValue;
    }

    //A.1

    public int calculateNetProductionFor24h(String machineName) {
        LocalDateTime dateTimeFrom = LocalDateTime.of(2018, 1, 7, 0, 0, 0);
        LocalDateTime dateTimeTo = LocalDateTime.of(2018, 1, 8, 1, 0, 0);
        int productionTotal = getVariableValue(machineName, "PRODUCTION", dateTimeFrom, dateTimeTo);
        int productionScrap = getVariableValue(machineName, "SCRAP", dateTimeFrom, dateTimeTo);
        return productionTotal - productionScrap;
    }

    // similar method to the one above but produces the result for the given period of time
    public int calculateNetProductionFor( String machineName, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        int productionTotal = getVariableValue(machineName, "PRODUCTION", dateTimeFrom, dateTimeTo);
        int productionScrap = getVariableValue(machineName, "SCRAP", dateTimeFrom, dateTimeTo);
        return productionTotal - productionScrap;
    }


    // A.2
    public float calculateScrapPercentageFor(String machineName) {
        LocalDateTime dateTimeFrom = LocalDateTime.of(2018, 1, 7, 0, 0, 0);
        LocalDateTime dateTimeTo = LocalDateTime.of(2018, 1, 8, 0, 0, 1);
        int productionTotal = getVariableValue(machineName, "PRODUCTION", dateTimeFrom, dateTimeTo);
        int productionScrap = getVariableValue(machineName, "SCRAP", dateTimeFrom, dateTimeTo);
        return (float) (productionScrap * 100 / productionTotal);

    }
    //A.4
    public Map<Integer, Integer> calculateHourlyProductionFor(String machineName) {
        // Method returning the Map where Key = hour range To and Value = amount produced net
        Map<Integer, Integer> hourGraph = new HashMap<>();
        // These two variables below can be thrown into the parameter so the method is more universal and it relates to almost all the methods in the project :-)
        LocalDateTime reportDateFrom = LocalDateTime.of(2018, 1, 7, 0, 0, 0);
        LocalDateTime reportDateTo = LocalDateTime.of(2018, 1, 7, 1, 0, 0);
        for (int i = 0; i < 24; i++) {
            // inside loop i calculate the net production for the first period of time ( from 00:00 to 01:00 ) and put it in MAP under key 0 and so on
            int netHourProd = calculateNetProductionFor(machineName, reportDateFrom, reportDateTo);
            hourGraph.put(i, netHourProd);
            reportDateFrom = reportDateFrom.plusHours(1);
            reportDateTo = reportDateTo.plusHours(1);
        }
        return hourGraph;
    }

    //B
    public String coreTemperatureStatusPerMachine(String machineName) {
        List<Production> productionList = productionRepository.findAllByMachineName(machineName);
        // one record in DB table "Production" is per 5 minutes
        int counter = 0;
        String machineStatus = "good";
        for (Production production : productionList) {
            // i start filtering the records by the core temp. variable
            if (production.getVariableName().equals("CORE TEMPERATURE")) {
                if (production.getValue() > 85 && production.getValue() <= 100) {
                    // if the temperature is within 85-100 degrees i add 1 to counter
                    counter++;
                    if (counter>=3){
                        // since every log is for 5 minutes if we have 3 or more records we have 15+ mins
                        machineStatus = "warning";
                    }
                } else if (production.getValue() > 100) {
                    machineStatus = "fatal";
                }
            }
        }
        return machineStatus;
    }
}
