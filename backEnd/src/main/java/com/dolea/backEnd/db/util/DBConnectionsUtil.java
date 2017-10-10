package com.dolea.backEnd.db.util;

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

    public static NoteRepository getRepository(EntityManager entityManager) {
        JpaRepositoryFactory factory = new JpaRepositoryFactory(entityManager);

        return factory.getRepository(NoteRepository.class);
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
