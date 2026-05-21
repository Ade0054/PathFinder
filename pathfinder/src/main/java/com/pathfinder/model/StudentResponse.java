package com.pathfinder.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/** One yes/no answer. clientUuid makes offline sync idempotent (no duplicate inserts). */
@Entity
@Table(name = "student_responses",
       uniqueConstraints = @UniqueConstraint(columnNames = "client_uuid"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StudentResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long responseId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id")
    private RiasecQuestion question;

    @Column(nullable = false)
    private boolean choice;     // true = yes, false = no

    /** Generated on the client before saving offline; the backend skips inserts it has seen. */
    @Column(name = "client_uuid", length = 36)
    private String clientUuid;

    @Column(nullable = false)
    private Instant answeredAt;
}
