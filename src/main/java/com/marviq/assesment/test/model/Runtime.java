package com.marviq.assesment.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Runtime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String machineName;
    private LocalDateTime datetime;
    boolean isrunning;


}
