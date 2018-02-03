package com.dolea.backEnd.db.repositories;

import com.dolea.backEnd.db.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByCreatedBy(String createdBy);
}
