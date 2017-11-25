package com.dolea.backEnd.db.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "script")
public class Script {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "comments")
    String comments;

    @Column(name = "date")
    String date;

    @Column(name = "username")
    String userName;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "script_execution",
            joinColumns = { @JoinColumn(name = "script_id") },
            inverseJoinColumns = { @JoinColumn(name = "execution_id") }
    )
    Set<Execution> scriptExecutions;
}
