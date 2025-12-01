package com.magentamause.cosybackend.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @Column(nullable = false)
    private String user_name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean default_password_reset;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        DEFAULT,
        ADMIN
    }
}
