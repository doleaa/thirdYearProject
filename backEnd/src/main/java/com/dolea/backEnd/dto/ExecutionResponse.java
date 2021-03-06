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
public class ExecutionResponse implements Serializable {
    private List<Map<String, String>> rows;
    private List<String> columns;
}
