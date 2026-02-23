package com.agendamento.servicos_tecnicos.dto;

import jakarta.validation.constraints.NotBlank;

public record AppointmentPatchDTO(
        String status,
        String notes
) {}
