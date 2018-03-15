package com.dolea.backEnd.db.util;

import com.dolea.backEnd.db.dao.Executor;
import com.dolea.backEnd.db.entities.Statement;
import lombok.experimental.UtilityClass;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class SQLExtractUtil {

    public static List<String> extractTahleNames(List<Statement> statementList) {
        List<String> allTableNames = statementList.stream()
                .map(statement -> statement.getSql())
                .flatMap(sql -> extractFromSingleSql(sql))
                .collect(Collectors.toList());

        return allTableNames;
    }

    public static List<String> extractCreateStatements(Set<String> tableNames, Executor executor) {
        Map<String, Map<String, String>> columnToTypeMaps = extractColumntToTypeMapsFromTables(tableNames, executor);

        return columnToTypeMaps.keySet().stream()
                .map(tableName -> {
                    String statement = String.format("CREATE TABLE %s(", tableName+"_sample");
                    for(Map.Entry<String, String> entry : columnToTypeMaps.get(tableName).entrySet()) {
                        statement += String.format("%s %s, ", entry.getKey(), entry.getValue());
                    }
                    statement += extractPrimaryKeyStringForTable(tableName, executor);
                    statement+=");";

                    return statement;
                })
                .collect(Collectors.toList());
    }

    public static List<String> extractInsertStatenents(Set<String> tableNames, Executor executor) {
        Map<String, Map<String, String>> columnToTypeMaps = extractColumntToTypeMapsFromTables(tableNames, executor);

        return columnToTypeMaps.keySet().stream()
                .map(tableName -> {
                    String statement = String.format("INSERT INTO %s(", tableName+"_sample");
                    for(Map.Entry<String, String> entry : columnToTypeMaps.get(tableName).entrySet()) {
                        statement += String.format("%s, ", entry.getKey());
                    }
                    statement = statement.substring(0, statement.length() - 2);
                    statement += ") VALUES ";

                    statement += extractInsertValuesString(
                            tableName,
                            columnToTypeMaps.get(tableName),
                            executor.runCommand(String.format("select * from %s limit 5;", tableName)));

                    statement += ";";

                    return statement;
                })
                .collect(Collectors.toList());
    }

    public static List<String> extractSelectStatements(Set<String> tableNames) {
        return tableNames.stream()
                .map(tableName -> String.format("SELECT * FROM %s;", tableName+"_sample"))
                .collect(Collectors.toList());
    }

    public static String extractInsertValuesString(
            String tableName, Map<String, String> columnsMap, List<Map<String, String>> translatedShowColResultSet) {

        List<String> valueStrings = translatedShowColResultSet.stream()
                .map(row -> {
                    String valueString = "(";

                    for(Map.Entry<String, String> entry : columnsMap.entrySet()) {
                        if(entry.getValue() != null && entry.getValue().contains("VARCHAR")) {
                            valueString += String.format("\'%s\', ", row.get(entry.getKey()));
                        } else if(entry.getValue() != null && entry.getValue().contains("DATE")) {
                            valueString += String.format("\'%s\', ", row.get(entry.getKey()));
                        } else {
                            valueString += String.format("%s, ", row.get(entry.getKey()));
                        }
                    }

                    valueString = valueString.substring(0, valueString.length() -2);
                    valueString += ")";

                    return valueString;
                })
                .collect(Collectors.toList());

        String insertValueStrings = valueStrings.get(0);

        for (int index = 1; index < valueStrings.size(); index++) {
            insertValueStrings += String.format(", %s", valueStrings.get(index));
        }

        return insertValueStrings;
    }

    public static List<String> extractDropStatements(Set<String> tableNames, Executor executor) {
        Map<String, Map<String, String>> columnToTypeMaps = extractColumntToTypeMapsFromTables(tableNames, executor);

        return columnToTypeMaps.keySet().stream()
                .map(tableName -> String.format("DROP TABLE %s;", tableName+"_sample"))
                .collect(Collectors.toList());
    }

    public static Map<String, Map<String, String>> extractColumntToTypeMapsFromTables(Set<String> tableNames, Executor executor) {
        return tableNames.stream()
                .collect(Collectors
                        .toMap(
                                tableName -> tableName,
                                tableName -> extractColumnToTypeMapFromTable(
                                        executor.runCommand(String.format("show columns from %s;", tableName))
                                )
                        )
                );
    }

    public static Map<String, String> extractColumnToTypeMapFromTable(List<Map<String, String>> translatedShowColResultSet) {
        return translatedShowColResultSet.stream()
                .collect(Collectors.toMap(row -> row.get("COLUMN_NAME"), row -> row.get("TYPE")));
    }

    public static String extractPrimaryKeyStringForTable(String tableName, Executor executor) {
        return extractPrimaryKey(executor.runCommand(String.format("show columns from %s;", tableName)));
    }

    public static String extractPrimaryKey(List<Map<String, String>> translatedShowColResultSet) {
        String primaryKeyMark = "PRI";
        List<String> primaryKeys = translatedShowColResultSet.stream()
                .filter(row -> primaryKeyMark.equals(row.get("KEY")))
                .map(row -> row.get("COLUMN_NAME"))
                .collect(Collectors.toList());

        String primaryKeysString = "PRIMARY KEY (";
        for(String key : primaryKeys) {
            primaryKeysString += String.format("%s, ", key);
        }

        primaryKeysString = primaryKeysString.substring(0, primaryKeysString.length() - 2);
        primaryKeysString += ")";

        return primaryKeysString;
    }

    private Stream<String> extractFromSingleSql(String sql) {
        try {
            return new TablesNamesFinder().getTableList(CCJSqlParserUtil.parse(sql)).stream();
        } catch (Exception e) {
            return null;
        }
    }
}
