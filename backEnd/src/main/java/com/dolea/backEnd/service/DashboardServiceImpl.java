package com.dolea.backEnd.service;

import com.dolea.backEnd.db.entities.Note;
import com.dolea.backEnd.dto.ExecutionInfo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.getExecutionDao;
import static com.dolea.backEnd.db.util.DBConnectionsUtil.getNoteDao;
import static com.dolea.backEnd.util.ThirdYearProjectConstants.DB_USERNAME_STRING;

public class DashboardServiceImpl implements DashboardService {
    @Override
    public List<Note> getAlllNotes(Map<String, String> requestMap) {
        return getNoteDao(requestMap).findByUserName(requestMap.get(DB_USERNAME_STRING));
    }

    @Override
    public List<ExecutionInfo> getAllExecutions(Map<String, String> requestMap) {
        return getExecutionDao(requestMap).findByUserName(requestMap.get(DB_USERNAME_STRING))
                .stream()
                .map(execution -> ExecutionInfo.builder()
                        .id(execution.getId())
                        .username(execution.getUserName())
                        .comments(execution.getComments())
                        .query(execution.getQuery())
                        .build())
                .collect(Collectors.toList());
    }
}
