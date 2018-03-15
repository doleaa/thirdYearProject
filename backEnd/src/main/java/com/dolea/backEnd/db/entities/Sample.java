package com.dolea.backEnd.db.entities;

import lombok.*;
import org.joda.time.LocalDateTime;
import java.time.LocalDateTime;

import javax.persistence.*;

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

    @Column(name = "columns")
    String columns;

    @Column(name = "create_statements")
    String createStatements;

    @Column(name = "insert_statements")
    String insertStatement;

    @Column(name = "drop_statements")
    String dropStatements;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "created_by")
    String createdBy;

    public Sample() {}
}
