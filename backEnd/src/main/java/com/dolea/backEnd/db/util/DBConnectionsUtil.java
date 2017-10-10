package com.dolea.backEnd.db.util;

import com.dolea.backEnd.db.dao.NoteDao;
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

    public static NoteDao getDao(Map<String, String> givenMap) {
        return new NoteDao(getEntityManager(givenMap));
    }

    public static NoteDao getDao(EntityManager entityManager) {
        return new NoteDao(entityManager);
    }

    public static NoteRepository getRepository(EntityManager entityManager) {
        JpaRepositoryFactory factory = new JpaRepositoryFactory(entityManager);

        return factory.getRepository(NoteRepository.class);
    }

    public static NoteRepository getRepository(Map<String, String> givenMap) {
        return getRepository(getEntityManager(givenMap));
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
