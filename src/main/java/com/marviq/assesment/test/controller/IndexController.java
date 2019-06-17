package com.marviq.assesment.test.controller;

import com.marviq.assesment.test.service.OeeService;
import com.marviq.assesment.test.service.ProductionService;
import com.marviq.assesment.test.service.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private OeeService oeeService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ProductionService productionService;


    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/results")
    public String SelectMachine(Model model, String machineName) {
//        // Assigment A
        int netProd = productionService.calculateNetProductionFor24h(machineName);
        float scrapPercent =  productionService.calculateScrapPercentageFor(machineName);
        float downTimePercent = runtimeService.calculateDownTimePercentage(machineName);
        Map<Integer, Integer> productionServiceMap = productionService.calculateHourlyProductionFor(machineName);
        //Assigment B
        String coreStatus = productionService.coreTemperatureStatusPerMachine(machineName);
        //Assigment C
        float oeeCalculated = oeeService.calculateOEE(machineName);

        model.addAttribute("machineName", machineName);
        model.addAttribute("netprod", netProd);
        model.addAttribute("scrapPercent", scrapPercent);
        model.addAttribute("downTimePercent", downTimePercent);
        model.addAttribute("productionMap", productionServiceMap);

        model.addAttribute("coreStatus", coreStatus);
        model.addAttribute("oeeCalculated", oeeCalculated);
        return "results";
    }


}
