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
@Table(name = "logbook_note")
public class Note implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "comments")
    String comments;

    @Column(name = "date")
    LocalDate date;

    @Column(name = "username")
    String userName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "logbook_note_execution_id")
    Set<Execution> executions;

    public Note() {}
}
