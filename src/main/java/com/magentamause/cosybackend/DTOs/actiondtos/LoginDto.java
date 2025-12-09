package com.magentamause.cosybackend.DTOs.actiondtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank private String username;
    @NotBlank private String password;
}
