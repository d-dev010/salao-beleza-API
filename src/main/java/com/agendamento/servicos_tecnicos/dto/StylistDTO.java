package com.agendamento.servicos_tecnicos.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record StylistDTO(
        Long id,
        String name,
        String email,
        String specialty,
        List<String> availableDays,
        Map<String, String> availableHours,
        LocalDateTime createdAt
) {}
