package com.pathfinder.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student_profiles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(length = 60)
    private String firstName;

    @Column(length = 60)
    private String lastName;

    private Integer age;

    @Column(length = 10)
    private String gender;

    /** e.g. JSS1, JSS2, SSS1 ... kept simple as text for now. */
    @Column(length = 10)
    private String gradeLevel;
}
