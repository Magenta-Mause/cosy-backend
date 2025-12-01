package com.magentamause.cosybackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity(name = "user_entity")
public class UserEntity {
    @Id private Long uuid;

    @OneToOne
    @JoinColumn(name = "login_entry_username")
    private LoginEntry loginEntry;
}
