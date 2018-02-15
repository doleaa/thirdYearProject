package com.dolea.backEnd.service;

import com.dolea.backEnd.db.dao.Executor;
import com.dolea.backEnd.db.entities.Comment;
import com.dolea.backEnd.dto.CommandDto;
import com.dolea.backEnd.dto.CommentDto;
import com.dolea.backEnd.dto.ExecutionInfo;
import com.dolea.backEnd.dto.ExecutionResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.getExecutor;
import static com.dolea.backEnd.util.ThirdYearProjectConstants.DB_USERNAME_STRING;

@RequiredArgsConstructor()
public class ActionManager {
    private final AuditService auditService;

    public ExecutionResponse executeCommand(CommandDto commandDto) {
        Executor executor = getExecutor(commandDto.getDbMap());

        Optional<List<Map<String, String>>> translatedResulSet = Optional
                .ofNullable(executor.runCommand(commandDto.getSqlCommand()));

        ExecutionInfo executionInfo = ExecutionInfo.builder()
                .username(commandDto.getDbMap().get(DB_USERNAME_STRING))
                .query(commandDto.getSqlCommand())
                .comments(commandDto.getComments())
                .result(translatedResulSet.orElse(null))
                .build();

        auditService.recordExecution(executionInfo, commandDto.getDbMap());

        return translatedResulSet.isPresent() ?
            new ExecutionResponse(translatedResulSet.get(),
                    translatedResulSet.get().get(0).keySet().stream().collect(Collectors.toList())) :
            new ExecutionResponse(null, null);
    }

    public Comment putComment(CommentDto commentDto, Integer executionId) {
        return auditService.updateExecutionComment(
                commentDto.getNewComment(), executionId, commentDto.getDbMap()
        );
    }
}
