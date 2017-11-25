package com.dolea.backEnd.service;

import com.dolea.backEnd.db.dao.ExecutionDao;
import com.dolea.backEnd.db.dao.NoteDao;
import com.dolea.backEnd.db.entities.Execution;
import com.dolea.backEnd.db.entities.Note;
import com.dolea.backEnd.dto.ExecutionInfo;
import com.dolea.backEnd.dto.NoteInfo;
import com.google.common.collect.ImmutableSet;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.getExecutionDao;
import static com.dolea.backEnd.db.util.DBConnectionsUtil.getNoteDao;

public class AuditServiceImpl implements AuditService {
    @Override
    public void recordExecution(ExecutionInfo executionInfo, Map<String, String> requestMap) {
        ExecutionDao executionDao = getExecutionDao(requestMap);

        Execution newExecution = Execution.builder()
                .query(executionInfo.getQuery())
                .userName(executionInfo.getUsername())
                .comments(executionInfo.getComments())
                .date(DateTime.now().toString())
                .build();

        executionDao.persist(newExecution);
    }

    @Override
    public void createNote(NoteInfo noteInfo, Map<String, String> requestMap) {
        ExecutionDao executionDao = getExecutionDao(requestMap);

        List<Execution> executions = executionDao.findAll(noteInfo.getNoteIds());
        Note newNote = Note.builder()
                .userName(noteInfo.getUsername())
                .comments(noteInfo.getComments())
                .executions(ImmutableSet.<Execution>builder()
                        .addAll(executions)
                        .build())
                .date(DateTime.now().toString())
                .build();

        executionDao.persistAllOf(newNote);
    }

    @Override
    public void updateNote(Integer noteId, NoteInfo noteInfo, Map<String, String> requestMap) {
        NoteDao noteDao = getNoteDao(requestMap);
        ExecutionDao executionDao = getExecutionDao(noteDao.getEntityManager());

        Note note = noteDao.findOne(noteId);

        List<Execution> executions = executionDao.findAll(noteInfo.getNoteIds());
        note.getExecutions().addAll(executions);
        note.setComments(noteInfo.getComments());

        executionDao.persistAllOf(note);
    }
}
