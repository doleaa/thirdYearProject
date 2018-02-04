package com.dolea.backEnd.db.util;

import com.dolea.backEnd.db.dao.ExecutionDao;
import com.dolea.backEnd.db.entities.Execution;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.SneakyThrows;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.*;
import static com.dolea.backEnd.util.ThirdYearProjectConstants.DB_PASSWORD_STRING;
import static com.dolea.backEnd.util.ThirdYearProjectConstants.DB_URL_STRING;
import static com.dolea.backEnd.util.ThirdYearProjectConstants.DB_USERNAME_STRING;
import static org.assertj.core.api.Assertions.assertThat;

public class DBConnectionsUtilTest {

    public static final String USERNAME = "some_username";
    public static final String COMMENTS = "dffdddddreatgsafasd333";
    public static Map<String, String> givenMap;

    @BeforeClass
    @SneakyThrows
    public static void setUp() {
        givenMap = new ImmutableMap.Builder<String, String>()
//                .put("url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
                .put(DB_URL_STRING, "jdbc:h2:/tmp/bang/test;AUTO_SERVER=TRUE")
                .put(DB_USERNAME_STRING, "sa")
                .put(DB_PASSWORD_STRING, "")
                .build();
    }

    @Ignore
    @Test
    @SneakyThrows
    public void getConnection_whenCalledWithMap_returnsConnectionInstance() {
        try (Connection connection = getConnection(givenMap);
             PreparedStatement preparedStatement = connection.prepareStatement("create table person;"))
        {

            boolean result = preparedStatement.execute();

            PreparedStatement preparedStatement1 = connection.prepareStatement("drop table person;");
            boolean response = preparedStatement1.execute();

            preparedStatement1.close();
        }
    }

//    @Test
//    public void
//
//    @Ignore
//    @Test
//    public void getNoteRepository_whenCalledForInserting_returnsRepositoryInstance() {
//        EntityManager entityManager = getEntityManager(givenMap);
//
//        NoteRepository repository = getNoteRepository(entityManager);
//
//        entityManager.getTransaction().begin();
//        repository.save(
//            Note.builder()
//                .date(DateTime.now().toString())
//                .userName(USERNAME)
//                .comments(COMMENTS)
//                .executions(ImmutableSet.<Execution>builder()
//                        .add(Execution.builder().date(DateTime.now().toString()).build())
//                        .build())
//                .build()
//        );
//        entityManager.getTransaction().commit();
//
//        List<Note> notes = repository.findByUserName(USERNAME);
//
//        assertThat(notes).isNotEmpty();
//    }
}