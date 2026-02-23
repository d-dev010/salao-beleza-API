package com.agendamento.servicos_tecnicos.dto;

public record BeautyServiceDTO(
        Long id,
        String name,
        String category,
        Integer duration,
        Double price,
        String description
) {}
