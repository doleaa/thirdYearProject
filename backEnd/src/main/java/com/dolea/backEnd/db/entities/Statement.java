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
@Table(name = "statement")
public class Statement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "sql")
    String sql;

    @Column(name = "created_at")
    DateTime createdAt;

    @Column(name = "created_by")
    String createdBy;

    public Statement() {}
}
