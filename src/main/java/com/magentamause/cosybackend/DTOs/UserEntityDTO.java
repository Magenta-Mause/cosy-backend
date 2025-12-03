package com.magentamause.cosybackend.DTOs;

import com.magentamause.cosybackend.entities.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntityDTO {

    private String username;
    private UserEntity.Role role;
}
