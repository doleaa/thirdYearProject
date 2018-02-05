package com.dolea.backEnd.service;

import com.dolea.backEnd.db.entities.Execution;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.getExecutionDao;
import static com.dolea.backEnd.util.ThirdYearProjectConstants.DB_USERNAME_STRING;

public class DashboardServiceImpl implements DashboardService {
    @Override
    public List<Execution> getAllExecutions(Map<String, String> requestMap) {
        return getExecutionDao(requestMap).findByExecutedBy(requestMap.get(DB_USERNAME_STRING))
                .stream()
                .sorted((a, b) ->
                        b.getRanAt().compareTo(a.getRanAt())
                )
                .collect(Collectors.toList());
    }
}
