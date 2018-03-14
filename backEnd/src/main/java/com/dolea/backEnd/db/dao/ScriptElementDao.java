package com.dolea.backEnd.db.dao;

import com.dolea.backEnd.db.entities.Script;
import com.dolea.backEnd.db.entities.ScriptElement;
import com.dolea.backEnd.db.repositories.ScriptElementRepository;
import lombok.Getter;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.getScriptElementRepository;

public class ScriptElementDao {
    @Getter
    private final EntityManager entityManager;
    private final ScriptElementRepository repository;

    public ScriptElementDao(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.repository = getScriptElementRepository(entityManager);
    }

    public ScriptElement persist(ScriptElement script) {
        entityManager.getTransaction().begin();
        ScriptElement persistedScript = repository.save(script);
        entityManager.getTransaction().commit();
        return persistedScript;
    }

    public List<ScriptElement> persistAllOf(Script script) {
        script.getElements().forEach(scriptElement -> scriptElement.setScript(script));

        return script.getElements()
                .stream()
                .map(this::persist)
                .collect(Collectors.toList());
    }

    public void deleteAllOf(Script script) {
        script.getElements().forEach(scriptElement -> scriptElement.setScript(script));

        script.getElements()
                .forEach(this::delete);
    }

    public List<ScriptElement> findAll() { return repository.findAll(); }

    public List<ScriptElement> findAll(Iterable<Integer> ids) { return repository.findAll(ids); }

    public ScriptElement findOne(Integer id) { return repository.findOne(id); }

    public void delete(ScriptElement script) {
        entityManager.getTransaction().begin();
        repository.delete(script);
        entityManager.getTransaction().commit();
    }
}
