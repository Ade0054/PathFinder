package com.pathfinder.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/** Computed score for one user, in one category, for one classroom test run. */
@Entity
@Table(name = "riasec_scores")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RiasecScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private RiasecCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    /** Number of "yes" answers in this category (0..30). */
    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private Instant computedAt;
}
