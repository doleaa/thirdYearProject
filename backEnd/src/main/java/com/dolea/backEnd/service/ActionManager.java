package com.dolea.backEnd.service;

import com.dolea.backEnd.db.dao.Executor;
import com.dolea.backEnd.db.entities.Comment;
import com.dolea.backEnd.db.entities.Sample;
import com.dolea.backEnd.db.entities.Script;
import com.dolea.backEnd.db.entities.ScriptElement;
import com.dolea.backEnd.dto.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.getExecutor;
import static com.dolea.backEnd.db.util.SQLExtractUtil.*;
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

    @SneakyThrows
    public Script createSampleForScript(RunScriptDto runScriptDto) {
        Script script = scripService.getScript(runScriptDto.getId(), runScriptDto.getDbMap());

        List<String> tableNames = extractTahleNames(script.getElements().stream()
                .filter(scriptElement -> scriptElement.getStatement() != null)
                .sorted(Comparator.comparing(ScriptElement::getPosition))
                .map(scriptElement -> scriptElement.getStatement())
                .collect(Collectors.toList()));

        List<String> createStatements =
                extractCreateStatements(Sets.newHashSet(tableNames), getExecutor(runScriptDto.getDbMap()));
        List<String> insertStatements =
                extractInsertStatenents(Sets.newHashSet(tableNames), getExecutor(runScriptDto.getDbMap()));
        List<String> selectStatements = extractSelectStatements(Sets.newHashSet(tableNames));
        List<String> droptStatements =
                extractDropStatements(Sets.newHashSet(tableNames), getExecutor(runScriptDto.getDbMap()));



        ObjectMapper mapper = new ObjectMapper();
        script.setSample(Sample.builder()
                .tables(mapper.writeValueAsString(tableNames))
                .createStatements(mapper.writeValueAsString(createStatements))
                .insertStatement(mapper.writeValueAsString(insertStatements))
                .dropStatements(mapper.writeValueAsString(droptStatements))
                .createdAt(LocalDateTime.now())
                .createdBy(runScriptDto.getDbMap().get(DB_USERNAME_STRING))
                .build());

        return scripService.persistScript(script, runScriptDto.getDbMap());
    }

    @SneakyThrows
    public List<ExecutionResponse> runScriptAgainstSample(RunScriptDto runScriptDto) {
        Script script = scripService.getScript(runScriptDto.getId(), runScriptDto.getDbMap());
        ObjectMapper mapper = new ObjectMapper();
        List<String> tables = mapper.readValue(script.getSample().getTables(), new TypeReference<List<String>>(){});
        List<String> createStatements = mapper.readValue(script.getSample().getCreateStatements(), new TypeReference<List<String>>(){});
        List<String> insertStatements = mapper.readValue(script.getSample().getInsertStatement(), new TypeReference<List<String>>(){});
        List<String> dropStatements = mapper.readValue(script.getSample().getDropStatements(), new TypeReference<List<String>>(){});

        List<ExecutionResponse> createResponse = createStatements.stream()
                .map(statemenet -> executeCommand(CommandDto.builder()
                        .dbMap(runScriptDto.getDbMap())
                        .sqlCommand(statemenet)
                        .build()))
                .collect(Collectors.toList());
        List<ExecutionResponse> insertResponse = insertStatements.stream()
                .map(statemenet -> executeCommand(CommandDto.builder()
                        .dbMap(runScriptDto.getDbMap())
                        .sqlCommand(statemenet)
                        .build()))
                .collect(Collectors.toList());

        List<ExecutionResponse> scriptSampleResponse = script.getElements().stream()
                .filter(scriptElement -> scriptElement.getStatement() != null)
                .sorted(Comparator.comparing(ScriptElement::getPosition))
                .map(scriptElement -> {
                    String sqlStatement = scriptElement.getStatement().getSql();
                    tables.forEach(tableName -> sqlStatement.replace(tableName, tableName+"_sample"));

                    return executeCommand(CommandDto.builder()
                            .sqlCommand(sqlStatement)
                            .dbMap(runScriptDto.getDbMap())
                            .build());
                })
                .collect(Collectors.toList());

        List<ExecutionResponse> dropResponse = dropStatements.stream()
                .map(statemenet -> executeCommand(CommandDto.builder()
                        .dbMap(runScriptDto.getDbMap())
                        .sqlCommand(statemenet)
                        .build()))
                .collect(Collectors.toList());

        return scriptSampleResponse;
    }

    public List<ExecutionResponse> runScript(RunScriptDto runScriptDto) {
        Script script = scripService.getScript(runScriptDto.getId(), runScriptDto.getDbMap());

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
