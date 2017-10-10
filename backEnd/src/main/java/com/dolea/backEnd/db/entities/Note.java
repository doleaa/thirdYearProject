package com.dolea.backEnd.db.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "logbook_note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "query")
    String query;

    @Column(name = "comments")
    String comments;

    @Column(name = "date")
    LocalDate date;

    @Column(name = "username")
    String userName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "logbook_note_execution_id")
    Set<Execution> executions;

    public Note() {}
}
