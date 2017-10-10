package com.dolea.backEnd.db.util;

import com.dolea.backEnd.db.entities.Execution;
import com.dolea.backEnd.db.entities.Note;
import com.dolea.backEnd.db.repositories.NoteRepository;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.SneakyThrows;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.getConnection;
import static com.dolea.backEnd.db.util.DBConnectionsUtil.getEntityManager;
import static com.dolea.backEnd.db.util.DBConnectionsUtil.getRepository;
import static org.assertj.core.api.Assertions.assertThat;

public class DBConnectionsUtilTest {

    public static final String USERNAME = "some_username";
    public static final String COMMENTS = "dffdddddreatgsafasd333";
    public static Map<String, String> givenMap;

    @BeforeClass
    @SneakyThrows
    public static void setUp() {
        givenMap = new ImmutableMap.Builder<String, String>()
                .put("url", "jdbc:h2:/tmp/bam/test;AUTO_SERVER=TRUE")
                .put("username", "sa")
                .put("password", "")
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

    @Test
    public void getRepository_whenCalledForInserting_returnsRepositoryInstance() {
        EntityManager entityManager = getEntityManager(givenMap);

        NoteRepository repository = getRepository(entityManager);

        entityManager.getTransaction().begin();
        repository.save(
            Note.builder()
                .date(LocalDate.now())
                .userName(USERNAME)
                .comments(COMMENTS)
                .executions(ImmutableSet.<Execution>builder()
                        .add(Execution.builder().date(LocalDate.now()).build())
                        .build())
                .build()
        );
        entityManager.getTransaction().commit();

        List<Note> notes = repository.findByUserName(USERNAME);

        assertThat(notes).isNotEmpty();
    }

    @Test
    public void getRepository_forQuerying_worksForNow() {
        NoteRepository repository = getRepository(getEntityManager(givenMap));

        List<Note> notes = repository.findByUserName(USERNAME);

        List<Note> lastNotes = notes.stream().filter(note -> note.getComments().equals(COMMENTS)).collect(Collectors.toList());
        assertThat(lastNotes.get(0).getExecutions()).isNotEmpty();

        List<Execution> executions = ImmutableList.copyOf(lastNotes.get(0).getExecutions());
        assertThat(executions.get(0)).isNotNull();
    }

    @Ignore
    @Test
    public void getRepository_forAnExecutionRemovalUpdate_thenCelebrate() {
        EntityManager entityManager = getEntityManager(givenMap);
        NoteRepository repository = getRepository(entityManager);

        List<Note> notes = repository.findByUserName(USERNAME);

        List<Note> myNotes = notes.stream().filter(note -> note.getExecutions().size() > 1).collect(Collectors.toList());
        Note note = myNotes.get(0);

        Execution execution = note.getExecutions().stream().collect(Collectors.toList()).get(0);
        note.getExecutions().remove(execution);

        entityManager.getTransaction().begin();
        repository.save(note);
        entityManager.getTransaction().commit();
    }

    @Test
    public void getRepository_forAnExecutionAddingUpdate_thenCelebrate() {
        EntityManager entityManager = getEntityManager(givenMap);
        NoteRepository repository = getRepository(entityManager);

        List<Note> notes = repository.findByUserName(USERNAME);

        Note note = notes.get(0);

        note.getExecutions().add(Execution.builder().date(LocalDate.now()).build());

        entityManager.getTransaction().begin();
        repository.save(note);
        entityManager.getTransaction().commit();
    }
}