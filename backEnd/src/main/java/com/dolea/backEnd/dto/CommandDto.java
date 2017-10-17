package com.dolea.backEnd.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Map;

@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CommandDto implements Serializable {
    private String sqlCommand;
    private String comments;
    private Map<String, String> dbMap;

    public CommandDto() {}
}
