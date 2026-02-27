package com.agendamento.servicos_tecnicos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stylists")
public class StylistController {

    @GetMapping
    public ResponseEntity<String> placeholder() {
        return ResponseEntity.ok("Stylist endpoints ainda não implementados.");
    }
}
