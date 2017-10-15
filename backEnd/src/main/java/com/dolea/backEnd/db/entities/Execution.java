package com.dolea.backEnd.db.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Execution execution = (Execution) o;

        if (id != null ? !id.equals(execution.id) : execution.id != null) return false;
        return userName != null ? userName.equals(execution.userName) : execution.userName == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }
}
