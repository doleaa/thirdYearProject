package com.dolea.backEnd.db.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "note")
public class Note implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "comments")
    String comments;

    @Column(name = "date")
    String date;

    @Column(name = "username")
    String userName;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "note_execution",
        joinColumns = { @JoinColumn(name = "note_id") },
        inverseJoinColumns = { @JoinColumn(name = "execution_id") }
    )
    Set<Execution> executions;

    public Note() {}
}
