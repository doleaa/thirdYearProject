package com.dolea.backEnd.service;

import com.dolea.backEnd.db.dao.Executor;
import com.dolea.backEnd.db.entities.Comment;
import com.dolea.backEnd.db.entities.Script;
import com.dolea.backEnd.dto.*;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.getExecutor;
import static com.dolea.backEnd.util.ThirdYearProjectConstants.DB_USERNAME_STRING;

@RequiredArgsConstructor()
public class ActionManager {
    private final AuditService auditService;
    private final ScriptService scripService;

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

    public Script createScript(ScriptDto scriptDto) {
        Script script = scripService
                .createScript(
                        ((LinkedHashMap) scriptDto.getScript()).get("title").toString(),
                        ((LinkedHashMap) scriptDto.getScript()).get("header").toString(),
                        ((ArrayList) ((LinkedHashMap) scriptDto.getScript()).get("elementList")),
                        scriptDto.getDbMap()
                );
        script.getElements().forEach(scriptElement -> scriptElement.setScript(null));
        return script;
    }
}
