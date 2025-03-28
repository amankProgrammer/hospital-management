package com.hospital_management.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;  // ✅ Ensure email field is present

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // ✅ Change role type to ENUM
    @Column(nullable = false)
    private Role role;  // e.g. PATIENT, DOCTOR, ADMIN

    @Column()
    private boolean approved = false;
}
