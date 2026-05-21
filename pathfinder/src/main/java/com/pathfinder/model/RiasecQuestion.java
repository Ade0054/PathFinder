package com.pathfinder.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "riasec_questions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RiasecQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    /** The master wording. Variants (rephrasings) live in QuestionVariant. */
    @Column(nullable = false, length = 500)
    private String questionText;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private RiasecCategory category;
}
