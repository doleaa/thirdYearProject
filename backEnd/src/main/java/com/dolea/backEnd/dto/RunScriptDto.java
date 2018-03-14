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
public class RunScriptDto implements Serializable {
    private Map<String, String> dbMap;
    private Integer id;

    public RunScriptDto() {}
}
