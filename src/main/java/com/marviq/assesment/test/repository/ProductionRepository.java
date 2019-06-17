package com.marviq.assesment.test.repository;

import com.marviq.assesment.test.model.Production;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductionRepository extends JpaRepository<Production, Long> {

    List<Production> findAllByMachineName(String machineName);

}
