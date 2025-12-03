package com.magentamause.cosybackend.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean defaultPasswordReset;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        OWNER,
        QUOTA_USER
    }
}
