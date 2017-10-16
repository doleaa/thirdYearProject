package com.dolea.backEnd.service;

import com.dolea.backEnd.dto.ExecutionDto;
import com.dolea.backEnd.dto.NoteDto;

public interface AuditService {

    void recordExecution(ExecutionDto executionDto);

    void createNote(NoteDto noteDto);

    void updateNote(NoteDto noteDto);
}
