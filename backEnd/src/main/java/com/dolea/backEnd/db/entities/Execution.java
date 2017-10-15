package com.dolea.backEnd.db.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "logbook_note_execution")
public class Execution implements Serializable {
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

    @ManyToOne
    @JoinColumn(name = "logbook_note_execution_id")
    Note note;

    public Execution() {}
}
