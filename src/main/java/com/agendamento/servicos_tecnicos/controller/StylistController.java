package com.agendamento.servicos_tecnicos.controller;

import com.agendamento.servicos_tecnicos.dto.StylistDTO;
import com.agendamento.servicos_tecnicos.dto.StylistCreateDTO;
import com.agendamento.servicos_tecnicos.dto.AppointmentDTO;
import com.agendamento.servicos_tecnicos.dto.BeautyServiceDTO;
import com.agendamento.servicos_tecnicos.service.StylistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stylists")
@RequiredArgsConstructor
public class StylistController {

    private final StylistService stylistService;

    @GetMapping
    public ResponseEntity<List<StylistDTO>> list(@RequestParam(value = "specialty", required = false) String specialty) {
        List<StylistDTO> stylists = stylistService.list(specialty);
        return ResponseEntity.ok(stylists);
    }

    @PostMapping
    public ResponseEntity<StylistDTO> create(@Valid @RequestBody StylistCreateDTO dto) {
        StylistDTO created = stylistService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StylistDTO> getById(@PathVariable Long id) {
        StylistDTO stylist = stylistService.getById(id);
        return ResponseEntity.ok(stylist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StylistDTO> update(@PathVariable Long id,
                                             @Valid @RequestBody StylistCreateDTO dto) {
        StylistDTO updated = stylistService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<StylistDTO> updateAvailability(@PathVariable Long id,
                                                         @RequestBody StylistCreateDTO dto) {
        StylistDTO updated = stylistService.updateAvailability(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}/schedule")
    public ResponseEntity<List<AppointmentDTO>> getSchedule(@PathVariable Long id,
                                                            @RequestParam(value = "date", required = false) String date) {
        List<AppointmentDTO> schedule = stylistService.getSchedule(id, date);
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/{id}/services")
    public ResponseEntity<List<BeautyServiceDTO>> getServices(@PathVariable Long id) {
        List<BeautyServiceDTO> services = stylistService.getServices(id);
        return ResponseEntity.ok(services);
    }
}
