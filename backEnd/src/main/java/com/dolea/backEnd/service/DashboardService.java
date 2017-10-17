package com.dolea.backEnd.service;

import com.dolea.backEnd.db.entities.Execution;
import com.dolea.backEnd.db.entities.Note;

import java.util.List;
import java.util.Map;

public interface DashboardService {

    List<Note> getAllPersonalNotes(Map<String, String> requestMap);

    List<Execution> getAllPersonalExecutions(Map<String, String> requestMap);
}
