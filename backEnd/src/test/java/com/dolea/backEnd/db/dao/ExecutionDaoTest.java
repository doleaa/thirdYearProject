package com.dolea.backEnd.db.dao;


import com.dolea.backEnd.db.entities.*;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import lombok.SneakyThrows;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.*;
import static com.dolea.backEnd.util.ThirdYearProjectConstants.DB_PASSWORD_STRING;
import static com.dolea.backEnd.util.ThirdYearProjectConstants.DB_URL_STRING;
import static com.dolea.backEnd.util.ThirdYearProjectConstants.DB_USERNAME_STRING;
import static org.assertj.core.api.Assertions.assertThat;

public class ExecutionDaoTest {
    private static final String USERNAME = "some_username";
    private static final String COMMENT = "dffdddddreatgsafasd333";
    private static final LocalDateTime DATE_TIME = LocalDateTime.now();
    private static Map<String, String> givenMap;
    private static ExecutionDao underTest;

    @BeforeClass
    @SneakyThrows
    public static void setUp() {
        givenMap = new ImmutableMap.Builder<String, String>()
                .put(DB_URL_STRING, "jdbc:h2:/tmp/bang/test;AUTO_SERVER=TRUE")
                .put(DB_USERNAME_STRING, "sa")
                .put(DB_PASSWORD_STRING, "")
                .build();
        underTest = getExecutionDao(givenMap);
    }

    @Ignore
    @Test
    public void persist_whenCalled_thenPersistsEverything() {
        Statement statement = Statement.builder()
                .sql("CREATE TABLE PERSON;")
                .createdAt(DATE_TIME)
                .createdBy(USERNAME)
                .build();
        Result result = Result.builder()
                .isError(false)
                .resultString("Table created!")
                .createdAt(DATE_TIME)
                .createdBy(USERNAME)
                .build();
        Comment comment = Comment.builder()
                .text(COMMENT)
                .createdAt(DATE_TIME)
                .createdBy(USERNAME)
                .build();
        Execution execution = Execution.builder()
                .statement(statement)
                .result(result)
                .comment(comment)
                .ranAt(DATE_TIME)
                .executedBy(USERNAME)
                .duration(3)
                .build();

        Execution persistedExecution = underTest.persist(execution);

        assertThat(persistedExecution).isEqualTo(execution);
    }

    @Ignore
    @Test
    public void findAll_whenCalled_thenFindsAll() {
        List<Execution> existingExecutions = underTest.findAll();

        assertThat(existingExecutions).isNotEmpty();
    }

    @Ignore
    @Test
    public void findAll_whenCommentUpdatedExternally_thenStillReturnsRightMapping() {
        CommentDao commentDao = getCommentDao(givenMap);

        List<Execution> executions = underTest.findAll();
        Comment comment = executions.get(0).getComment();
        comment.setText("some random text");
        commentDao.persist(comment);

        Execution execution = underTest.findAll().get(0);
        assertThat(execution.getComment().getText()).isEqualTo("some random text");
    }

    @Ignore
    @Test
    public void delete_whenCalled_thenDeletesWithCascade() {
        Execution existingExecution = underTest.findOne(2);

        underTest.delete(existingExecution);

        List<Execution> allRemainingExecutions = underTest.findAll();

        assertThat(allRemainingExecutions).isEmpty();
    }

    @Test
    public void scriptDao_whenCalledToPersist_thenPersistsAsExpected() {
        Comment newComment = Comment.builder()
                .text(COMMENT)
                .createdBy(USERNAME)
                .createdAt(DATE_TIME)
                .build();

        Statement newStatement = Statement.builder()
                .sql("SELECT * FROM LIFE;")
                .createdBy(USERNAME)
                .createdAt(DATE_TIME)
                .build();

        ScriptElement firstElement = ScriptElement.builder()
                .comment(newComment)
                .createdAt(DATE_TIME)
                .createdBy(USERNAME)
                .position(1)
                .build();

        ScriptElement secondElement = ScriptElement.builder()
                .statement(newStatement)
                .createdAt(DATE_TIME)
                .createdBy(USERNAME)
                .position(2)
                .build();

        Script newScript = Script.builder()
                .title("Some Title")
                .header("Some Header....LOLZ")
                .elements(Sets.newHashSet(firstElement, secondElement))
                .createdAt(DATE_TIME)
                .createdBy(USERNAME)
                .build();

        ScriptDao underTest = getScriptDao(givenMap);
        ScriptElementDao underTestElement = getScriptElementDao(givenMap);

        List<Script> allScripts = underTest.findAll();
        allScripts.stream().forEach(script -> {
            script.getElements().forEach(element -> underTestElement.delete(element));
            underTest.delete(script);
        });

        Script persistedScript = underTest.persist(newScript);
        allScripts = underTest.findAll();

        assertThat(persistedScript).isNotNull();
        assertThat(allScripts).isNotEmpty();
        assertThat(allScripts.size()).isEqualTo(1);
        assertThat(allScripts.get(0).getElements().contains(firstElement)).isTrue();
        assertThat(allScripts.get(0).getElements().contains(secondElement)).isTrue();

        Script editing = allScripts.get(0);
        secondElement = editing.getElements().stream().collect(Collectors.toList()).get(0);
        editing.setElements(null);

        editing = underTest.persist(editing);
        editing.setElements(Sets.newHashSet(secondElement));
        editing.setElements(underTestElement.persistAllOf(editing).stream().collect(Collectors.toSet()));
        persistedScript = underTest.findOne(editing.getId());

        allScripts = underTest.findAll();

        assertThat(persistedScript).isNotNull();
        assertThat(allScripts).isNotEmpty();
        assertThat(allScripts.size()).isEqualTo(1);
        assertThat(persistedScript.getElements().size()).isEqualTo(1);
        assertThat(allScripts.get(0).getElements().size()).isEqualTo(1);
        assertThat(persistedScript.getElements().contains(secondElement)).isTrue();
        assertThat(allScripts.get(0).getElements().contains(secondElement)).isTrue();
    }
}