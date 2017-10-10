package com.dolea.backEnd.db.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "logbook_note_execution")
public class Execution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "date")
    LocalDate date;

    public Execution() {}
}
