package com.dolea.backEnd.service;

import com.dolea.backEnd.db.entities.Comment;
import com.dolea.backEnd.db.entities.Execution;
import com.dolea.backEnd.db.entities.Result;
import com.dolea.backEnd.db.entities.Statement;
import com.dolea.backEnd.dto.ExecutionInfo;
import com.dolea.backEnd.dto.ExecutionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.getExecutionDao;
import static com.dolea.backEnd.util.ThirdYearProjectConstants.DB_USERNAME_STRING;

public class AuditServiceImpl implements AuditService {
    @Override
    @SneakyThrows
    public void recordExecution(ExecutionInfo executionInfo, Map<String, String> requestMap) {
        Comment comment = null;
        Result result = null;
        String resultString;
        ObjectMapper mapper = new ObjectMapper();

        if(executionInfo.getResult() != null) {
            resultString = mapper.writeValueAsString(new ExecutionResponse(executionInfo.getResult(),
                    executionInfo.getResult().get(0).keySet().stream().collect(Collectors.toList())));
        } else {
            resultString = mapper.writeValueAsString(new ExecutionResponse(null, null));
        }

        if(executionInfo.getResult() != null) {
            result = Result.builder()
                    .resultString(resultString)
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

    @Override
    public Comment updateExecutionComment(String newComment, Integer executionId, Map<String, String> requestMap) {
        Execution execution = getExecutionDao(requestMap).findOne(executionId);

        if(execution.getComment() != null) {
            Comment existingComment = execution.getComment();

            existingComment.setText(newComment);
            existingComment.setUpdatedAt(LocalDateTime.now());
            existingComment.setUpdatedBy(requestMap.get(DB_USERNAME_STRING));
        } else {
            Comment comment = Comment.builder()
                    .text(newComment)
                    .createdAt(LocalDateTime.now())
                    .createdBy(requestMap.get(DB_USERNAME_STRING))
                    .build();
            execution.setComment(comment);
        }

        return getExecutionDao(requestMap).persist(execution).getComment();
    }
}
