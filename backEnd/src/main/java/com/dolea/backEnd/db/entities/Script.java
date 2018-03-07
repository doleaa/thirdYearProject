package com.dolea.backEnd.db.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "script")
public class Script implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "title")
    String title;

    @Column(name = "header")
    String header;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "created_by")
    String createdBy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "script")
    Set<ScriptElement> elements;

    public Script() {}
}
