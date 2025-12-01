package com.magentamause.cosybackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "user_entity")
public class UserEntity {
    @Id
    private Long uuid;
}
