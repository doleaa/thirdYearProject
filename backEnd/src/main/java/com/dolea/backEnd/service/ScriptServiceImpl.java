package com.dolea.backEnd.service;

import com.dolea.backEnd.db.entities.Comment;
import com.dolea.backEnd.db.entities.Script;
import com.dolea.backEnd.db.entities.ScriptElement;
import com.dolea.backEnd.db.entities.Statement;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.getExecutionDao;
import static com.dolea.backEnd.db.util.DBConnectionsUtil.getScriptDao;
import static com.dolea.backEnd.db.util.DBConnectionsUtil.getScriptElementDao;
import static com.dolea.backEnd.util.ThirdYearProjectConstants.DB_USERNAME_STRING;

public class ScriptServiceImpl implements ScriptService {
    @Override
    public Script createScript(String title, String header, List<Object> elementList, Map<String, String> requestMap) {
        Set<ScriptElement> scriptElements = elementList
                .stream()
                .map(element -> {
                    if(((LinkedHashMap) element).get("execution") != null) {

                        Statement statement = getExecutionDao(requestMap)
                                .findOne(Integer.valueOf(
                                        ((LinkedHashMap) ((LinkedHashMap) element).get("execution"))
                                        .get("id").toString()
                                ))
                                .getStatement();
                        statement.setId(null);

                        return ScriptElement.builder()
                                .position(Integer.valueOf(((LinkedHashMap) element).get("position").toString()))
                                .statement(statement)
                                .createdBy(requestMap.get(DB_USERNAME_STRING))
                                .createdAt(LocalDateTime.now())
                                .build();

                    } else {

                        Comment comment = Comment.builder()
                                .createdBy(requestMap.get(DB_USERNAME_STRING))
                                .createdAt(LocalDateTime.now())
                                .text(((LinkedHashMap) ((LinkedHashMap) element).get("comment")).get("text").toString())
                                .build();

                        return ScriptElement.builder()
                                .position(Integer.valueOf(((LinkedHashMap) element).get("position").toString()))
                                .comment(comment)
                                .createdBy(requestMap.get(DB_USERNAME_STRING))
                                .createdAt(LocalDateTime.now())
                                .build();

                    }
                })
                .collect(Collectors.toSet());

        Script newScript = getScriptDao(requestMap)
                .persist(
                        Script.builder()
                            .title(title)
                            .header(header)
                            .createdBy(requestMap.get(DB_USERNAME_STRING))
                            .createdAt(LocalDateTime.now())
                            .build()
                );
        newScript.setElements(scriptElements);
        newScript.setElements(getScriptElementDao(requestMap).persistAllOf(newScript)
                .stream().collect(Collectors.toSet()));

        return newScript;
    }

    @Override
    public List<Script> findAllScripts(Map<String, String> requestMap) {
        return getScriptDao(requestMap).findAll();
    }

    @Override
    public List<Script> findByCreatedBy(Map<String, String> requestMap) {
        List<Script> list = getScriptDao(requestMap).findByCreatedBy(requestMap.get(DB_USERNAME_STRING));
        list.stream().forEach(script -> script.getElements().forEach(scriptElement -> scriptElement.setScript(null)));

        return list.stream()
                .sorted((a, b) ->
                        b.getCreatedAt().compareTo(a.getCreatedAt())
                ).collect(Collectors.toList());
    }
}
