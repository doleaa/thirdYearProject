package com.dolea.backEnd.service;

import com.dolea.backEnd.db.entities.Execution;
import com.dolea.backEnd.db.entities.Note;

import java.util.List;

public class DashboardServiceImpl implements DashboardService {
    @Override
    public List<Note> getAllNotesForUser(String username) {
        return null;
    }

    @Override
    public List<Execution> getAllExecutionsForUser(String username) {
        return null;
    }
}
