package com.pathfinder.model;

import com.pathfinder.model.enums.TestStatus;
import jakarta.persistence.*;
import lombok.*;

/** Lock/unlock state of one RIASEC category for one classroom (whole class, not per student). */
@Entity
@Table(name = "classroom_test_status",
       uniqueConstraints = @UniqueConstraint(columnNames = {"classroom_id", "category_id"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ClassroomTestStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private RiasecCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 12)
    private TestStatus status;
}
