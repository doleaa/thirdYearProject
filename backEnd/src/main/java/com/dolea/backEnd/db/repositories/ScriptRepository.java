package com.dolea.backEnd.db.repositories;

import com.dolea.backEnd.db.entities.Script;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScriptRepository extends JpaRepository<Script, Integer> {

    List<Script> findByCreatedBy(String createdBy);
}
