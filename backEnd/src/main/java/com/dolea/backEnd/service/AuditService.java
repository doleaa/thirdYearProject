package com.dolea.backEnd.service;

import com.dolea.backEnd.db.entities.Comment;
import com.dolea.backEnd.dto.ExecutionInfo;

import java.util.Map;

public interface AuditService {

    void recordExecution(ExecutionInfo executionInfo, Map<String, String> requestMap);

    Comment updateExecutionComment(String newComment, Integer executionId, Map<String, String> requestMap);
}
