package com.dolea.backEnd.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@Builder
public class ExecutionInfo implements Serializable {
    private final String query;
    private final String comments;
    private final String username;
    private final List<Map<String, String>> result;
}
