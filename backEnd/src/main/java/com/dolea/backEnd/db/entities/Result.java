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
@Table(name = "result")
public class Result implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "is_error")
    boolean isError;

    @Column(name = "error_message")
    String errorMessage;

    @Column(name = "result_string")
    String resultString;

    @Column(name = "created_at")
    DateTime createdAt;

    @Column(name = "created_by")
    String createdBy;

    public Result() {}
}
