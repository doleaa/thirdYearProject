package com.dolea.backEnd.db.repositories;

import com.dolea.backEnd.db.entities.Execution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExecutionRepository extends JpaRepository<Execution, Integer> {

    List<Execution> findByExecutedBy(String executedBy);
}
