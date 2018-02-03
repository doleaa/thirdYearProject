package com.dolea.backEnd.service;

import com.dolea.backEnd.dto.ExecutionInfo;

import java.util.List;
import java.util.Map;

public class DashboardServiceImpl implements DashboardService {
    @Override
    public List<ExecutionInfo> getAllExecutions(Map<String, String> requestMap) {
//        return getExecutionDao(requestMap).findByUserName(requestMap.get(DB_USERNAME_STRING))
//                .stream()
//                .map(execution -> ExecutionInfo.builder()
//                        .id(execution.getId())
//                        .username(execution.getUserName())
//                        .comments(execution.getComments())
//                        .query(execution.getQuery())
//                        .date(execution.getDate())
//                        .build())
//                .sorted((a, b) ->
//                        DateTime.parse(b.getDate()).compareTo(DateTime.parse(a.getDate()))
//                )
//                .collect(Collectors.toList());
        return null;
    }
}
