package com.agendamento.servicos_tecnicos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClientCreateDTO(
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email,
        @NotBlank(message = "Phone is required") @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number") String phone
) {}
