package com.dolea.backEnd.db.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "execution")
public class Execution implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "query")
    String query;

    @Column(name = "comments")
    String comments;

    @Column(name = "date")
    String date;

    @Column(name = "username")
    String userName;

    @ManyToMany(mappedBy = "executions")
    Set<Note> notes;

    @ManyToMany(mappedBy = "scriptExecutions")
    Set<Script> scripts;

    public Execution() {}
}
