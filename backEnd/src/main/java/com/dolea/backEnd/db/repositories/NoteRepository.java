package com.dolea.backEnd.db.repositories;

import com.dolea.backEnd.db.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {

    List<Note> findByUserName(String userName);
}
