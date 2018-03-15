package com.dolea.backEnd.db.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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

    @Column(name = "sql", columnDefinition = "TEXT")
    String sql;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "created_by")
    String createdBy;

    public Statement() {}
}
