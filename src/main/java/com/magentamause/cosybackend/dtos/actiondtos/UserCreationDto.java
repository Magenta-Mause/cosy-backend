package com.magentamause.cosybackend.dtos.actiondtos;

import com.magentamause.cosybackend.annotations.ValidUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreationDto {
    @ValidUsername private String username;

    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
}
