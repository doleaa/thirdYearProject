package com.dolea.backEnd.db.dao;


import com.dolea.backEnd.db.entities.Comment;
import com.dolea.backEnd.db.entities.Execution;
import com.dolea.backEnd.db.entities.Result;
import com.dolea.backEnd.db.entities.Statement;
import com.google.common.collect.ImmutableMap;
import lombok.SneakyThrows;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.getExecutionDao;
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

    @Test
    public void findAll_whenCalled_thenFindsAll() {
        List<Execution> existingExecutions = underTest.findAll();

        assertThat(existingExecutions).isNotEmpty();
    }
}