package com.dolea.backEnd.service;

import com.dolea.backEnd.db.entities.Execution;

import java.util.List;
import java.util.Map;

public interface DashboardService {

    List<Execution> getAllExecutions(Map<String, String> requestMap);
}
