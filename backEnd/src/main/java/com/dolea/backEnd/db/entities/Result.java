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
@Table(name = "result")
public class Result implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "is_error")
    boolean isError;

    @Column(name = "error_message")
    String errorMessage;

    @Column(name = "result_string", columnDefinition = "TEXT")
    String resultString;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "created_by")
    String createdBy;

    public Result() {}
}
