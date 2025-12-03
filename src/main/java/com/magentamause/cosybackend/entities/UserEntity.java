package com.magentamause.cosybackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_entity")
public class UserEntity {
    @Id private String uuid;
    private String username;
    private String password;
}
