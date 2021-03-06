package com.dolea.backEnd.db.dao;

import com.dolea.backEnd.db.entities.Execution;
import com.dolea.backEnd.db.repositories.ExecutionRepository;
import lombok.Getter;

import javax.persistence.EntityManager;

import java.util.List;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.getExecutionRepository;

public class ExecutionDao {
    @Getter
    private final EntityManager entityManager;
    private final ExecutionRepository repository;

    public ExecutionDao (EntityManager entityManager) {
        this.entityManager = entityManager;
        this.repository = getExecutionRepository(entityManager);
    }

    public Execution persist(Execution execution) {
        entityManager.getTransaction().begin();
        Execution persistedExecution = repository.save(execution);
        entityManager.getTransaction().commit();
        return persistedExecution;
    }

    public List<Execution> findByExecutedBy(String executedBy) { return repository.findByExecutedBy(executedBy); }

    public List<Execution> findAll() { return repository.findAll(); }

    public List<Execution> findAll(Iterable<Integer> ids) { return repository.findAll(ids); }

    public Execution findOne(Integer id) { return repository.findOne(id); }

    public void delete(Execution execution) {
        entityManager.getTransaction().begin();
        repository.delete(execution);
        entityManager.getTransaction().commit();
    }
}
