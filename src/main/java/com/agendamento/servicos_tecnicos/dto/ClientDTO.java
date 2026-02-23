package com.agendamento.servicos_tecnicos.dto;

import java.time.LocalDateTime;

public record ClientDTO(
        Long id,
        String name,
        String email,
        String phone,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
