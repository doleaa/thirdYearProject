package com.dolea.backEnd.db.util;

import com.google.common.collect.ImmutableMap;
import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class DBMapsUtil {

    public static Map<String, String> getConnectionMap(Map<String, String> givenMap) {
        return ImmutableMap.<String, String>builder()
                .put("driver", getDriver())
                .putAll(givenMap)
                .build();
    }

    public static Map<String, String> getHibernateMap(Map<String, String> givenMap) {
        return ImmutableMap.<String, String>builder()
                .put("hibernate.connection.driver_class", getDriver())
                .putAll(givenMapToHibernateFormat(givenMap))
                .put("hibernate.connection.dialect", getDialect())
                .put("hibernate.connection.show-sql", "true")
                .put("hibernate.hbm2ddl.auto", "update")
                .build();
    }

    private static Map<String, String> givenMapToHibernateFormat(Map<String, String> map) {
        return ImmutableMap.<String, String>builder()
                .put("hibernate.connection.url", map.get("url"))
                .put("hibernate.connection.username", map.get("username"))
                .put("hibernate.connection.password", map.get("password"))
                .build();
    }

    // TODO: Replace this with call to Injected Map or Enum of Drivers
    private String getDriver() {
        return "org.h2.Driver";
    }

    // TODO: Replace this with call to Injected Map or Enum of Dialects
    private String getDialect() {
        return "org.hibernate.dialect.H2Dialect";
    }
}
