package com.dolea.backEnd.db.dao;

import com.dolea.backEnd.db.entities.Script;
import com.dolea.backEnd.db.repositories.ScriptRepository;
import lombok.Getter;

import javax.persistence.EntityManager;

import java.util.List;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.getScriptRepository;

public class ScriptDao {
    @Getter
    private final EntityManager entityManager;
    private final ScriptRepository repository;

    public ScriptDao(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.repository = getScriptRepository(entityManager);
    }

    public Script persist(Script script) {
        entityManager.getTransaction().begin();
        Script persistedScript = repository.save(script);
        entityManager.getTransaction().commit();
        return persistedScript;
    }

    public List<Script> findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

    public List<Script> findAll() {
        return repository.findAll();
    }

    public Script findOne(Integer id) {
        return repository.findOne(id);
    }

    public void delete(Script note) {
        entityManager.getTransaction().begin();
        repository.delete(note);
        entityManager.getTransaction().commit();
    }
}
