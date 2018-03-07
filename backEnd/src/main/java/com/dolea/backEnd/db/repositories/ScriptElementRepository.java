package com.dolea.backEnd.db.repositories;

import com.dolea.backEnd.db.entities.ScriptElement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScriptElementRepository extends JpaRepository<ScriptElement, Integer> {
}
