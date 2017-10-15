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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (id != null ? !id.equals(note.id) : note.id != null) return false;
        return userName != null ? userName.equals(note.userName) : note.userName == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }
}
