package com.dolea.backEnd.service;

import com.dolea.backEnd.db.entities.Execution;
import com.dolea.backEnd.db.entities.Note;

import java.util.List;

public interface DashboardService {

    List<Note> getAllNotesForUser(String username);

    List<Execution> getAllExecutionsForUser(String username);
}
