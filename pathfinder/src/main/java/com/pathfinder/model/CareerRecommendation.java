package com.pathfinder.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "career_recommendations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CareerRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long careerId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private RiasecCategory category;

    @Column(nullable = false, length = 120)
    private String careerTitle;

    @Column(length = 800)
    private String description;

    /** Comma-separated for simplicity (e.g. "English, Mathematics, Physics, Chemistry"). */
    @Column(length = 300)
    private String jambSubjects;

    @Column(length = 300)
    private String waecSubjects;

    /** Comma-separated Nigerian universities offering this course. */
    @Column(length = 500)
    private String universities;
}
