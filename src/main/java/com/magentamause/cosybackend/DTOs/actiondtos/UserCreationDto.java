package com.magentamause.cosybackend.DTOs.actiondtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserCreationDto {
    @NotBlank private String username;
    @NotBlank private String password;
}
