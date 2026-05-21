package com.pathfinder.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "riasec_categories")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RiasecCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    /** Realistic, Investigative, Artistic, Social, Enterprising, Conventional. */
    @Column(nullable = false, unique = true, length = 30)
    private String categoryName;

    /** Single letter code: R, I, A, S, E, C. */
    @Column(nullable = false, unique = true, length = 1)
    private String code;

    /** Offline-friendly preliminary blurb shown when this category wins (no AI needed). */
    @Column(length = 600)
    private String summaryTemplate;
}
