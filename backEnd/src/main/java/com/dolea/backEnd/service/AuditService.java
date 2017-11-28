package com.dolea.backEnd.service;

import com.dolea.backEnd.dto.ExecutionInfo;

import java.util.Map;

public interface AuditService {

    void recordExecution(ExecutionInfo executionInfo, Map<String, String> requestMap);
}
