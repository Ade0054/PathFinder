package com.pathfinder.model;

import jakarta.persistence.*;
import lombok.*;

/** One of ~10 AI-generated rephrasings of a master question. Generated once, stored, served offline. */
@Entity
@Table(name = "question_variants")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class QuestionVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long variantId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id")
    private RiasecQuestion question;

    @Column(nullable = false, length = 500)
    private String variantText;
}
