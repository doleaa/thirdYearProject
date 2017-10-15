package com.dolea.backEnd.db.dao;

import com.dolea.backEnd.db.entities.Note;
import com.dolea.backEnd.db.repositories.NoteRepository;
import lombok.Getter;

import javax.persistence.EntityManager;

import java.util.List;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.getNoteRepository;

public class NoteDao {
    @Getter
    private final EntityManager entityManager;
    private final NoteRepository repository;

    public NoteDao (EntityManager entityManager) {
        this.entityManager = entityManager;
        this.repository = getNoteRepository(entityManager);
    }

    public Note persist(Note note) {
        entityManager.getTransaction().begin();
        Note persistedNote = repository.save(note);
        entityManager.getTransaction().commit();
        return persistedNote;
    }

    public List<Note> findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

    public List<Note> findAll() {
        return repository.findAll();
    }

    public Note findOne(Integer id) {
        return repository.findOne(id);
    }

    public void delete(Note note) {
        entityManager.getTransaction().begin();
        repository.delete(note);
        entityManager.getTransaction().commit();
    }
}
