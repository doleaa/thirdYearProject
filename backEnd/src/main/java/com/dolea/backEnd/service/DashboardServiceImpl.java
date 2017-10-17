package com.dolea.backEnd.service;

import com.dolea.backEnd.db.entities.Execution;
import com.dolea.backEnd.db.entities.Note;

import java.util.List;
import java.util.Map;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.getExecutionDao;
import static com.dolea.backEnd.db.util.DBConnectionsUtil.getNoteDao;
import static com.dolea.backEnd.util.ThirdYearProjectConstants.DB_USERNAME_STRING;

public class DashboardServiceImpl implements DashboardService {
    @Override
    public List<Note> getAllPersonalNotes(Map<String, String> requestMap) {
        return getNoteDao(requestMap).findByUserName(requestMap.get(DB_USERNAME_STRING));
    }

    @Override
    public List<Execution> getAllPersonalExecutions(Map<String, String> requestMap) {
        return getExecutionDao(requestMap).findByUserName(requestMap.get(DB_USERNAME_STRING));
    }
}
