package com.agendamento.servicos_tecnicos.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record BeautyServiceCreateDTO(
        @NotBlank(message = "Name is required") String name,
        String category,
        @Min(value = 1, message = "Duration must be at least 1 minute") Integer duration,
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0") Double price,
        String description
) {}
