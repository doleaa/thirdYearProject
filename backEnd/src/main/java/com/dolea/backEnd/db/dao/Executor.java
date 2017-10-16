package com.dolea.backEnd.db.dao;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class Executor {
    private final Connection connection;

    @SneakyThrows
    public List<Map<String, String>> runCommand(String sql) {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            boolean resultSetAvailable = ps.execute();

            if (resultSetAvailable) {
                return resultSetToListOfRows(ps.getResultSet());
            } else {
                return null;
            }
        }
    }

    @SneakyThrows
    private List<Map<String, String>> resultSetToListOfRows(ResultSet rs) {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();

        List<Map<String, String>> list = new ArrayList<>();

        while (rs.next()){
            Map<String, String> row = new HashMap<>(columns);
            for(int i=1; i<=columns; ++i){
                row.put(md.getColumnName(i),rs.getString(i));
            }
            list.add(row);
        }

        return list;
    }
}
