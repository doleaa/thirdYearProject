package com.dolea.backEnd.db.util;

import com.dolea.backEnd.db.dao.*;
import com.dolea.backEnd.db.repositories.CommentRepository;
import com.dolea.backEnd.db.repositories.ExecutionRepository;
import com.dolea.backEnd.db.repositories.ScriptElementRepository;
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

    public static ScriptDao getScriptDao(Map<String, String> givenMap) {
        return new ScriptDao(getEntityManager(givenMap));
    }

    public static ScriptRepository getScriptRepository(EntityManager entityManager) {
        JpaRepositoryFactory factory = new JpaRepositoryFactory(entityManager);

        return factory.getRepository(ScriptRepository.class);
    }

    public static ScriptElementDao getScriptElementDao(Map<String, String> givenMap) {
        return new ScriptElementDao(getEntityManager(givenMap));
    }

    public static ScriptElementRepository getScriptElementRepository(EntityManager entityManager) {
        JpaRepositoryFactory factory = new JpaRepositoryFactory(entityManager);

        return factory.getRepository(ScriptElementRepository.class);
    }

    public static Executor getExecutor(Map<String, String> givenMap) {
        return getExecutor(getConnection(givenMap));
    }

    private static Executor getExecutor(Connection connection) {
        return new Executor(connection);
    }

    public static ExecutionDao getExecutionDao(Map<String, String> givenMap) {
        return new ExecutionDao(getEntityManager(givenMap));
    }

    public static ExecutionDao getExecutionDao(EntityManager entityManager) {
        return new ExecutionDao(entityManager);
    }

    public static ExecutionRepository getExecutionRepository(EntityManager entityManager) {
        JpaRepositoryFactory factory = new JpaRepositoryFactory(entityManager);

        return factory.getRepository(ExecutionRepository.class);
    }

    public static ExecutionRepository getExecutionRepository(Map<String, String> givenMap) {
        return getExecutionRepository(getEntityManager(givenMap));
    }

    public static CommentDao getCommentDao(Map<String, String> givenMap) {
        return new CommentDao(getEntityManager(givenMap));
    }

    public static CommentRepository getCommentRepository(EntityManager entityManager) {
        JpaRepositoryFactory factory = new JpaRepositoryFactory(entityManager);

        return factory.getRepository(CommentRepository.class);
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
