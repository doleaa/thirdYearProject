package com.dolea.backEnd.db.util;

import com.dolea.backEnd.db.dao.ExecutionDao;
import com.dolea.backEnd.db.dao.NoteDao;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.*;
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

    @Ignore
    @Test
    public void getNoteRepository_whenCalledForInserting_returnsRepositoryInstance() {
        EntityManager entityManager = getEntityManager(givenMap);

        NoteRepository repository = getNoteRepository(entityManager);

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

    @Ignore
    @Test
    public void getNoteRepository_forQuerying_worksForNow() {
        NoteRepository repository = getNoteRepository(getEntityManager(givenMap));

        List<Note> notes = repository.findByUserName(USERNAME);

        List<Note> lastNotes = notes.stream().filter(note -> note.getComments().equals(COMMENTS)).collect(Collectors.toList());
        assertThat(lastNotes.get(0).getExecutions()).isNotEmpty();

        List<Execution> executions = ImmutableList.copyOf(lastNotes.get(0).getExecutions());
        assertThat(executions.get(0)).isNotNull();
    }

    @Ignore
    @Test
    public void getNoteRepository_forAnExecutionRemovalUpdate_thenCelebrate() {
        EntityManager entityManager = getEntityManager(givenMap);
        NoteRepository repository = getNoteRepository(entityManager);

        List<Note> notes = repository.findByUserName(USERNAME);

        List<Note> myNotes = notes.stream().filter(note -> note.getExecutions().size() > 1).collect(Collectors.toList());
        Note note = myNotes.get(0);

        Execution execution = note.getExecutions().stream().collect(Collectors.toList()).get(0);
        note.getExecutions().remove(execution);

        entityManager.getTransaction().begin();
        repository.save(note);
        entityManager.getTransaction().commit();
    }

    @Ignore
    @Test
    public void getNoteRepository_forAnExecutionAddingUpdate_thenCelebrate() {
        EntityManager entityManager = getEntityManager(givenMap);
        NoteRepository repository = getNoteRepository(entityManager);

        List<Note> notes = repository.findByUserName(USERNAME);

        Note note = notes.get(0);

        note.getExecutions().add(Execution.builder().date(LocalDate.now()).build());

        entityManager.getTransaction().begin();
        repository.save(note);
        entityManager.getTransaction().commit();
    }

    @Ignore
    @Test
    public void getEntityManager_forAnExecutionAddingUpdate_thenCelebrate() {
        NoteDao noteDao = getNoteDao(givenMap);

        List<Note> notes = noteDao.findByUserName(USERNAME);

        Note note = notes.get(0);

        note.getExecutions().add(Execution.builder().date(LocalDate.now()).build());

        noteDao.persist(note);
    }

    @Ignore
    @Test
    public void getExecutionRepository_forAddingAnOrphanExecution_thenCelebrate() {
        ExecutionDao executionDao = getExecutionDao(givenMap);

        Execution execution = Execution.builder()
                .userName(USERNAME)
                .date(LocalDate.now()).build();

        executionDao.persist(execution);

        List<Execution> executions = executionDao.findByUserName(USERNAME);

        assertThat(executions).isNotEmpty();
    }

    @Ignore
    @Test
    public void getNoteRepository_forAdoptingAnOrphanExecution_thenCelebrate() {
        ExecutionDao executionDao = getExecutionDao(givenMap);
        NoteDao noteDao = getNoteDao(givenMap);

        Note newNote = Note.builder().userName(USERNAME).comments(COMMENTS).date(LocalDate.now()).build();

        newNote = noteDao.persist(newNote);

        Note sameNote = noteDao.findOne(newNote.getId());
        Execution execution = executionDao.findByUserName(USERNAME).get(0);

        sameNote.setExecutions(ImmutableSet.of(
                execution,
                Execution.builder()
                        .userName(USERNAME)
                        .date(LocalDate.now())
                        .query("queryNo2")
                        .build())
        );

        List<Execution> executions = executionDao.persistAllOf(sameNote);
    }

    @Ignore
    @Test
    public void getNoteRepository_forInspection_thenCelebrate() {
        NoteDao noteDao = getNoteDao(givenMap);

        Note note = noteDao.findByUserName(USERNAME).get(0);

        assertThat(note.getExecutions()).isNotEmpty();
    }
}