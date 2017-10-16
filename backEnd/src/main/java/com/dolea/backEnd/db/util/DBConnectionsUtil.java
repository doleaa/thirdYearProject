package com.dolea.backEnd.db.util;

import com.dolea.backEnd.db.dao.ExecutionDao;
import com.dolea.backEnd.db.dao.Executor;
import com.dolea.backEnd.db.dao.NoteDao;
import com.dolea.backEnd.db.repositories.ExecutionRepository;
import com.dolea.backEnd.db.repositories.NoteRepository;
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

    public static ExecutionDao getExecutionDao(Map<String, String> givenMap) {
        return new ExecutionDao(getEntityManager(givenMap));
    }

    public static ExecutionDao getExecutionDao(EntityManager entityManager) {
        return new ExecutionDao(entityManager);
    }

    public static NoteDao getNoteDao(EntityManager entityManager) {
        return new NoteDao(entityManager);
    }

    public static NoteRepository getNoteRepository(EntityManager entityManager) {
        JpaRepositoryFactory factory = new JpaRepositoryFactory(entityManager);

        return factory.getRepository(NoteRepository.class);
    }

    public static ExecutionRepository getExecutionRepository(EntityManager entityManager) {
        JpaRepositoryFactory factory = new JpaRepositoryFactory(entityManager);

        return factory.getRepository(ExecutionRepository.class);
    }

    public static NoteRepository getNoteRepository(Map<String, String> givenMap) {
        return getNoteRepository(getEntityManager(givenMap));
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
                        connectionMap.get("url"),
                        connectionMap.get("username"),
                        connectionMap.get("password")
                );
    }
}
