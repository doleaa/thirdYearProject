package com.dolea.backEnd.service;

import com.dolea.backEnd.dto.ExecutionInfo;
import com.dolea.backEnd.dto.NoteInfo;

import java.util.Map;

public interface AuditService {

    void recordExecution(ExecutionInfo executionInfo, Map<String, String> requestMap);

    void createNote(NoteInfo noteInfo, Map<String, String> requestMap);

    void updateNote(Integer noteId, NoteInfo noteInfo, Map<String, String> requestMap);
}
