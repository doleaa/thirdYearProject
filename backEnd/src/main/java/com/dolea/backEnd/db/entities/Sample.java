package com.dolea.backEnd.db.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "sample")
public class Sample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "tables", columnDefinition = "TEXT")
    String tables;

    @Column(name = "create_statements", columnDefinition = "TEXT")
    String createStatements;

    @Column(name = "insert_statements", columnDefinition = "TEXT")
    String insertStatement;

    @Column(name = "drop_statements", columnDefinition = "TEXT")
    String dropStatements;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "created_by")
    String createdBy;

    public Sample() {}
}
