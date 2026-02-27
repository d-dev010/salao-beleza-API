package com.agendamento.servicos_tecnicos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/availability")
public class AvailabilityController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAvailability(@RequestParam(value = "stylistId", required = false) Long stylistId,
                                                               @RequestParam(value = "date", required = false) String date) {
        // Controller simplificado: retorna apenas os parâmetros recebidos.
        return ResponseEntity.ok(Map.of(
                "stylistId", stylistId,
                "date", date
        ));
    }
}
