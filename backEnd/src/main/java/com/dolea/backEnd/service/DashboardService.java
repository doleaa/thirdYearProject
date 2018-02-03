package com.dolea.backEnd.service;

import com.dolea.backEnd.dto.ExecutionInfo;

import java.util.List;
import java.util.Map;

public interface DashboardService {

    List<ExecutionInfo> getAllExecutions(Map<String, String> requestMap);
}
