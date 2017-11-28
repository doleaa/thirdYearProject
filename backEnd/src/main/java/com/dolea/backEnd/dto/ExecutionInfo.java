package com.dolea.backEnd.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Builder
@Setter
@EqualsAndHashCode
@ToString
public class ExecutionInfo implements Serializable {
    private Integer id;
    private String query;
    private String comments;
    private String username;
    private List<Map<String, String>> result;
}
