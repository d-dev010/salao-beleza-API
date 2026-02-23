package com.agendamento.servicos_tecnicos.dto;

import jakarta.validation.constraints.NotBlank;

public record AgendamentoPatchDTO(
        @NotBlank(message = "Status é obrigatório") String status
) {}
