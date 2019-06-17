package com.marviq.assesment.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Production {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String machineName;
    private String variableName;
    private LocalDateTime datetimeFrom;
    private LocalDateTime datetimeTo;
    private int value;


}
