package com.marviq.assesment.test.service;


import com.marviq.assesment.test.model.Runtime;
import com.marviq.assesment.test.repository.RuntimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;


@Service
public class RuntimeService {

    @Autowired
    private RuntimeRepository runtimeRepository;

    // My approach here was to measure the times between the machine was running and not
    // For this i compared the two records in between when the status changed from 1 to 0 and counted the time difference between them
    public int getUpTimeInMinutes(String machineName) {
        List<Runtime> runTime = runtimeRepository.findAllByMachineName(machineName);
        LocalDateTime reportDateFrom = LocalDateTime.of(2018, 1, 7, 0, 0, 0);
        // Method will calculate the difference between the two records when the machine had the isRunning =1 and 0
        String previousRecordTime = "00:00";
        String actualRecordTime = "00:00";
        int minutesUptime = 0;
        for (Runtime runtime : runTime) {
            // first i filter from the date requested
            if (runtime.getDatetime().isAfter(reportDateFrom)) {
                if (runtime.isIsrunning()) {
                    // below i save the record where machine started working and convert it first to LocalTime and then to String of format HH:mm
                    previousRecordTime = runtime.getDatetime().toLocalTime().toString();
                } else {
                    // below i save the record where machine stopped working and also convert it to string
                    actualRecordTime = runtime.getDatetime().toLocalTime().toString();
                }
            }
            // here i calculate the difference between the two records found and sum it up
            minutesUptime += calculateDifferenceBetweenStrings(previousRecordTime, actualRecordTime);
        }
        return minutesUptime;
    }


    private int calculateDifferenceBetweenStrings(String date1, String date2) {
        // in this method i take two Strings and calculate the difference between the value when the machine was down and up, returning the int value with minutes
        // first date to int number of minutes
        int datePost = toMins(date1);
        // second date to int number of minutes
        int datePre = toMins(date2);
        return datePost - datePre;
    }


    // Method i found on StackOverflow to calculate the minutes from the String of format HH:mm
    private static int toMins(String s) {
        String[] hourMin = s.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int hoursInMins = hour * 60;
        return hoursInMins + mins;
    }

    public int calculateDownTimeInMinutes(String machineName) {
        int minutesPerDay = 1440;
        return minutesPerDay - getUpTimeInMinutes(machineName);
    }
    //A.3
    public float calculateDownTimePercentage(String machineName){
        int downTime = calculateDownTimeInMinutes(machineName);
        return (float) ((downTime*100)/1440);
    }





}


