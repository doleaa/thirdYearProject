package com.dolea.backEnd.db.entities;

import lombok.*;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

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

    @OneToOne(cascade = CascadeType.ALL)
    Statement statement;

    @OneToOne(cascade = CascadeType.ALL)
    Result result;

    @Column(name = "executed_by")
    String executedBy;

    @Column(name = "ran_at")
    DateTime ranAt;

    @Column(name = "duration")
    Integer duration;

    @OneToOne(cascade = CascadeType.ALL)
    Comment comment;

    public Execution() {}
}
