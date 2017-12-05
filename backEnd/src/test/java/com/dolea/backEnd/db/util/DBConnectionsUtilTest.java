package com.dolea.backEnd.db.util;

import com.dolea.backEnd.db.dao.ExecutionDao;
import com.dolea.backEnd.db.dao.NoteDao;
import com.dolea.backEnd.db.dao.ScriptDao;
import com.dolea.backEnd.db.entities.Execution;
import com.dolea.backEnd.db.entities.Note;
import com.dolea.backEnd.db.entities.Script;
import com.dolea.backEnd.db.repositories.NoteRepository;
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

    @Ignore
    @Test
    public void getNoteRepository_whenCalledForInserting_returnsRepositoryInstance() {
        EntityManager entityManager = getEntityManager(givenMap);

        NoteRepository repository = getNoteRepository(entityManager);

        entityManager.getTransaction().begin();
        repository.save(
            Note.builder()
                .date(DateTime.now().toString())
                .userName(USERNAME)
                .comments(COMMENTS)
                .executions(ImmutableSet.<Execution>builder()
                        .add(Execution.builder().date(DateTime.now().toString()).build())
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

        note.getExecutions().add(Execution.builder().date(DateTime.now().toString()).build());

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

        note.getExecutions().add(Execution.builder().date(DateTime.now().toString()).build());

        noteDao.persist(note);
    }

    @Ignore
    @Test
    public void getExecutionRepository_forAddingAnOrphanExecution_thenCelebrate() {
        ExecutionDao executionDao = getExecutionDao(givenMap);

        Execution execution = Execution.builder()
                .userName(USERNAME)
                .date(DateTime.now().toString()).build();

        executionDao.persist(execution);

        List<Execution> executions = executionDao.findByUserName(USERNAME);

        assertThat(executions).isNotEmpty();
    }

    @Ignore
    @Test
    public void getNoteRepository_forAdoptingAnOrphanExecution_thenCelebrate() {
        ExecutionDao executionDao = getExecutionDao(givenMap);
        NoteDao noteDao = getNoteDao(givenMap);
        ScriptDao scriptDao = getScriptDao(givenMap);

        Note newNote = Note.builder().userName(USERNAME).comments(COMMENTS).date(DateTime.now().toString()).build();
        Script newScript = Script.builder().userName(USERNAME).comments(COMMENTS).date(DateTime.now().toString()).build();

        newNote = noteDao.persist(newNote);
        newScript = scriptDao.persist(newScript);

        Note sameNote = noteDao.findOne(newNote.getId());
        Script sameScript = scriptDao.findOne(newScript.getId());
        Execution execution = executionDao.findByUserName(USERNAME).get(0);

        sameNote.setExecutions(new HashSet<>());
        sameScript.setScriptExecutions(new HashSet<>());


        sameNote.getExecutions().add(execution);
        sameScript.getScriptExecutions().add(execution);

        Note note = noteDao.persist(sameNote);
        Script script = scriptDao.persist(sameScript);
        assertThat(note).isNotNull();
        assertThat(script).isNotNull();
    }

    @Ignore
    @Test
    public void getNoteRepository_forInspection_thenCelebrate() {
        NoteDao noteDao = getNoteDao(givenMap);

        Note note = noteDao.findByUserName(USERNAME).get(0);

        assertThat(note.getExecutions()).isNotEmpty();
    }
}