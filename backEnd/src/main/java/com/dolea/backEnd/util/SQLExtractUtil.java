package com.dolea.backEnd.util;

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

    private Stream<String> extractFromSingleSql(String sql) {
        try {
            return new TablesNamesFinder().getTableList(CCJSqlParserUtil.parse(sql)).stream();
        } catch (Exception e) {
            return null;
        }
    }
}
