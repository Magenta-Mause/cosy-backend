package com.magentamause.cosybackend.DTOs.entitydtos;

import com.magentamause.cosybackend.entities.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserEntityDto {
    private String uuid;
    private String username;
    private UserEntity.Role role;
}
