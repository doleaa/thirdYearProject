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
public class CommentDto implements Serializable {
    private String newComment;
    private Map<String, String> dbMap;
}
