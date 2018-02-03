package com.dolea.backEnd.service;

import com.dolea.backEnd.db.dao.ExecutionDao;
import com.dolea.backEnd.db.entities.Execution;
import com.dolea.backEnd.dto.ExecutionInfo;
import org.joda.time.DateTime;

import java.util.Map;

import static com.dolea.backEnd.db.util.DBConnectionsUtil.getExecutionDao;

public class AuditServiceImpl implements AuditService {
    @Override
    public void recordExecution(ExecutionInfo executionInfo, Map<String, String> requestMap) {
//        ExecutionDao executionDao = getExecutionDao(requestMap);
//
//        Execution newExecution = Execution.builder()
//                .(executionInfo.getQuery())
//                .userName(executionInfo.getUsername())
//                .comments(executionInfo.getComments())
//                .date(DateTime.now().toString())
//                .build();
//
//        executionDao.persist(newExecution);
    }
}
