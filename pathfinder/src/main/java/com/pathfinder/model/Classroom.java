package com.pathfinder.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "classrooms")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classroomId;

    @Column(nullable = false, length = 100)
    private String classroomName;

    /** Short alphanumeric join code, e.g. MCP-4821. Unique. */
    @Column(nullable = false, unique = true, length = 16)
    private String joinCode;

    /** The counsellor who owns this classroom. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "counsellor_id")
    private User counsellor;

    @Column(nullable = false)
    private Instant createdAt;
}
