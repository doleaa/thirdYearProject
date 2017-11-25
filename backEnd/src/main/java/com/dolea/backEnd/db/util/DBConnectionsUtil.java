package com.dolea.backEnd.db.util;

import com.dolea.backEnd.db.dao.ExecutionDao;
import com.dolea.backEnd.db.dao.Executor;
import com.dolea.backEnd.db.dao.NoteDao;
import com.dolea.backEnd.db.dao.ScriptDao;
import com.dolea.backEnd.db.repositories.ExecutionRepository;
import com.dolea.backEnd.db.repositories.NoteRepository;
import com.dolea.backEnd.db.repositories.ScriptRepository;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

import static com.dolea.backEnd.db.util.DBMapsUtil.getConnectionMap;
import static com.dolea.backEnd.db.util.DBMapsUtil.getHibernateMap;
import static com.dolea.backEnd.util.ThirdYearProjectConstants.DB_PASSWORD_STRING;
import static com.dolea.backEnd.util.ThirdYearProjectConstants.DB_URL_STRING;
import static com.dolea.backEnd.util.ThirdYearProjectConstants.DB_USERNAME_STRING;


@UtilityClass
public class DBConnectionsUtil {

    public static Executor getExecutor(Map<String, String> givenMap) {
        return getExecutor(getConnection(givenMap));
    }

    private static Executor getExecutor(Connection connection) {
        return new Executor(connection);
    }

    public static NoteDao getNoteDao(Map<String, String> givenMap) {
        return new NoteDao(getEntityManager(givenMap));
    }

    public static ScriptDao getScriptDao(Map<String, String> givenMap) {
        return new ScriptDao(getEntityManager(givenMap));
    }

    public static ExecutionDao getExecutionDao(Map<String, String> givenMap) {
        return new ExecutionDao(getEntityManager(givenMap));
    }

    public static ExecutionDao getExecutionDao(EntityManager entityManager) {
        return new ExecutionDao(entityManager);
    }

    public static ScriptDao getScriptDao(EntityManager entityManager) { return new ScriptDao(entityManager); }

    public static NoteDao getNoteDao(EntityManager entityManager) {
        return new NoteDao(entityManager);
    }

    public static NoteRepository getNoteRepository(EntityManager entityManager) {
        JpaRepositoryFactory factory = new JpaRepositoryFactory(entityManager);

        return factory.getRepository(NoteRepository.class);
    }

    public static ScriptRepository getScriptRepository(EntityManager entityManager) {
        JpaRepositoryFactory factory = new JpaRepositoryFactory(entityManager);

        return factory.getRepository(ScriptRepository.class);
    }

    public static ExecutionRepository getExecutionRepository(EntityManager entityManager) {
        JpaRepositoryFactory factory = new JpaRepositoryFactory(entityManager);

        return factory.getRepository(ExecutionRepository.class);
    }

    public static NoteRepository getNoteRepository(Map<String, String> givenMap) {
        return getNoteRepository(getEntityManager(givenMap));
    }

    public static ScriptRepository getScriptRepository(Map<String, String> givenMap) {
        return getScriptRepository(getEntityManager(givenMap));
    }

    public static ExecutionRepository getExecutionRepository(Map<String, String> givenMap) {
        return getExecutionRepository(getEntityManager(givenMap));
    }

    public static EntityManager getEntityManager(Map<String, String> givenMap) {
        return Persistence
                .createEntityManagerFactory("thirdYearProject", getHibernateMap(givenMap))
                .createEntityManager();
    }

    @SneakyThrows
    public static Connection getConnection(Map<String, String> givenMap) {
        Map<String, String> connectionMap = getConnectionMap(givenMap);

        Class.forName(connectionMap.get("driver"));

        return DriverManager
                .getConnection(
                        connectionMap.get(DB_URL_STRING),
                        connectionMap.get(DB_USERNAME_STRING),
                        connectionMap.get(DB_PASSWORD_STRING)
                );
    }
}
