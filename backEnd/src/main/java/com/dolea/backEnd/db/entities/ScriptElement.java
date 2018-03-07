package com.dolea.backEnd.db.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "script_element")
public class ScriptElement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    Statement statement;

    @OneToOne(cascade = CascadeType.ALL)
    Comment comment;

    @Column(name = "position")
    Integer position;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "created_by")
    String createdBy;

    @Column(name = "updated_by")
    String updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "script_id")
    Script script;

    public ScriptElement() {}
}
