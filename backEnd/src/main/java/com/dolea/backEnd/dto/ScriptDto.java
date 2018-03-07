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
public class ScriptDto implements Serializable {
    private Map<String, String> dbMap;
    private Object script;

    public ScriptDto() {}
}
