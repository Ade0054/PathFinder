package com.pathfinder.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    /** STUDENT or COUNSELLOR. Stored without the "ROLE_" prefix; added in security config. */
    @Column(nullable = false, unique = true, length = 30)
    private String roleName;
}
