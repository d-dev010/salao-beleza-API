package com.agendamento.servicos_tecnicos.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AppointmentDTO(
        Long id,
        Long clientId,
        ClientDTO client,
        Long stylistId,
        StylistDTO stylist,
        List<Long> serviceIds,
        List<BeautyServiceDTO> services,
        LocalDateTime appointmentDate,
        String status,
        Integer totalDuration,
        Double totalPrice,
        String notes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
