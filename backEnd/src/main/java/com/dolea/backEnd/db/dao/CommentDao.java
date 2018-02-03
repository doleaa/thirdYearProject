package com.dolea.backEnd.db.dao;

import com.dolea.backEnd.db.entities.Comment;
import com.dolea.backEnd.db.repositories.CommentRepository;
import lombok.Getter;

import javax.persistence.EntityManager;

import java.util.List;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.getCommentRepository;

public class CommentDao {
    @Getter
    private final EntityManager entityManager;
    private final CommentRepository repository;

    public CommentDao(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.repository = getCommentRepository(entityManager);
    }
    
    public Comment persist(Comment comment) {
        entityManager.getTransaction().begin();
        Comment persistedComment = repository.save(comment);
        entityManager.getTransaction().commit();
        return persistedComment;
    }

    public List<Comment> findByCreatedBy(String createdBy) { return repository.findByCreatedBy(createdBy); }

    public List<Comment> findAll() { return repository.findAll(); }

    public List<Comment> findAll(Iterable<Integer> ids) { return repository.findAll(ids); }

    public Comment findOne(Integer id) { return repository.findOne(id); }

    public void delete(Comment comment) {
        entityManager.getTransaction().begin();
        repository.delete(comment);
        entityManager.getTransaction().commit();
    }
}
