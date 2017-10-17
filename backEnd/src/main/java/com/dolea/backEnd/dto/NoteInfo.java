package com.dolea.backEnd.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Builder
public class NoteInfo implements Serializable {
    private final String comments;
    private final String username;
    private final List<Integer> noteIds;
}
