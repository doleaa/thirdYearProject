package com.dolea.backEnd.service;

import com.dolea.backEnd.db.entities.Script;

import java.util.List;
import java.util.Map;

public interface ScriptService {
    Script createScript(String title, String header, List<Object> elementList, Map<String, String> requestMap);

    Script updateScript(Integer id, String title, String header, List<Object> elementList, Map<String, String> requestMap);

    List<Script> findAllScripts(Map<String, String> requestMap);

    List<Script> findByCreatedBy(Map<String, String> requestMap);

    Script getScript(Integer id, Map<String, String> requestMap);
}
