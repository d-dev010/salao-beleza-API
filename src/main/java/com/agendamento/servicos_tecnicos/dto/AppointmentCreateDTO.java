package com.agendamento.servicos_tecnicos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record AppointmentCreateDTO(
        @NotNull(message = "Client ID is required") Long clientId,
        @NotNull(message = "Stylist ID is required") Long stylistId,
        @NotEmpty(message = "Service IDs are required") List<Long> serviceIds,
        @NotNull(message = "Appointment date is required") LocalDateTime appointmentDate,
        String notes
) {}
