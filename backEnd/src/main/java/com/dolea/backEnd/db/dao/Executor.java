package com.dolea.backEnd.db.dao;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

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
    private List<Map<String, String>> resultSetToListOfRows(ResultSet rs){
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();

        List<Map<String, String>> list = new ArrayList<>();

        while (rs.next()){
            Map<String, String> row = new HashMap<>(columns);

            IntStream.range(1, columns).forEach(i -> {
                try {
                    row.put(md.getColumnName(i),rs.getString(i));
                } catch (SQLException e) {}
            });

            list.add(row);
        }

        return list;
    }
}
