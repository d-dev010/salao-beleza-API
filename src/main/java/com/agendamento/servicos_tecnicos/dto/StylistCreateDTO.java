package com.agendamento.servicos_tecnicos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Map;

public record StylistCreateDTO(
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email,
        @NotBlank(message = "Specialty is required") String specialty,
        List<String> availableDays,
        Map<String, String> availableHours
) {}
