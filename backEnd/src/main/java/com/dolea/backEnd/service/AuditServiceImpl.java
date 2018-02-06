package com.dolea.backEnd.service;

import com.dolea.backEnd.db.entities.Comment;
import com.dolea.backEnd.db.entities.Execution;
import com.dolea.backEnd.db.entities.Result;
import com.dolea.backEnd.db.entities.Statement;
import com.dolea.backEnd.dto.ExecutionInfo;

import java.time.LocalDateTime;
import java.util.Map;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.getExecutionDao;

public class AuditServiceImpl implements AuditService {
    @Override
    public void recordExecution(ExecutionInfo executionInfo, Map<String, String> requestMap) {
        Comment comment = null;
        Result result = null;

        if(executionInfo.getResult() != null) {
            result = Result.builder()
                    .resultString(executionInfo.getResult().toString())
                    .createdAt(LocalDateTime.now())
                    .createdBy(executionInfo.getUsername())
                    .build();
        }

        if(executionInfo.getComments() != "") {
            comment = Comment.builder()
                    .text(executionInfo.getComments())
                    .createdAt(LocalDateTime.now())
                    .createdBy(executionInfo.getUsername())
                    .build();
        }

        Statement statement = Statement.builder()
                .sql(executionInfo.getQuery())
                .createdAt(LocalDateTime.now())
                .createdBy(executionInfo.getUsername())
                .build();

        Execution execution = Execution.builder()
                .statement(statement)
                .result(result)
                .comment(comment)
                .ranAt(LocalDateTime.now())
                .executedBy(executionInfo.getUsername())
                .build();

        getExecutionDao(requestMap).persist(execution);
    }
}
