package com.marviq.assesment.test.repository;

import com.marviq.assesment.test.model.Runtime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RuntimeRepository extends JpaRepository<Runtime, Long> {

    List<Runtime> findAllByMachineName(String machineName);
}
