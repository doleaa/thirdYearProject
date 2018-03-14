package com.dolea.backEnd.service;

import com.dolea.backEnd.db.dao.Executor;
import com.dolea.backEnd.db.entities.Comment;
import com.dolea.backEnd.db.entities.Script;
import com.dolea.backEnd.db.entities.ScriptElement;
import com.dolea.backEnd.dto.*;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.getExecutor;
import static com.dolea.backEnd.util.SQLExtractUtil.*;
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

    public List<ExecutionResponse> runScript(RunScriptDto runScriptDto) {
        Script script = scripService.getScript(runScriptDto.getId(), runScriptDto.getDbMap());

        List<String> tableNames = extractTahleNames(script.getElements().stream()
                .filter(scriptElement -> scriptElement.getStatement() != null)
                .sorted(Comparator.comparing(ScriptElement::getPosition))
                .map(scriptElement -> scriptElement.getStatement())
                .collect(Collectors.toList()));

        Map<String, Map<String, String>> mapsOfColumnsToTypes =
                extractColumntToTypeMapsFromTables(Sets.newHashSet(tableNames), getExecutor(runScriptDto.getDbMap()));

        List<String> createStatements =
                extractCreateStatements(Sets.newHashSet(tableNames), getExecutor(runScriptDto.getDbMap()));
        List<String> droptStatements =
                extractDropStatements(Sets.newHashSet(tableNames), getExecutor(runScriptDto.getDbMap()));

        return script.getElements().stream()
            .filter(scriptElement -> scriptElement.getStatement() != null)
            .sorted(Comparator.comparing(ScriptElement::getPosition))
            .map(scriptElement ->
                executeCommand(CommandDto.builder()
                .sqlCommand(scriptElement.getStatement().getSql())
                .dbMap(runScriptDto.getDbMap())
                .build())
            )
            .collect(Collectors.toList());
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

    public Script updateScript(ScriptDto scriptDto) {
        Script script = scripService
                .updateScript(
                        Integer.valueOf(((LinkedHashMap) scriptDto.getScript()).get("id").toString()),
                        ((LinkedHashMap) scriptDto.getScript()).get("title").toString(),
                        ((LinkedHashMap) scriptDto.getScript()).get("header").toString(),
                        ((ArrayList) ((LinkedHashMap) scriptDto.getScript()).get("elementList")),
                        scriptDto.getDbMap()
                );
        script.getElements().forEach(scriptElement -> scriptElement.setScript(null));
        return script;
    }
}
