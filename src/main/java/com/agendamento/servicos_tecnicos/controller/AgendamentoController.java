package com.agendamento.servicos_tecnicos.controller;

import com.agendamento.servicos_tecnicos.dto.AppointmentCreateDTO;
import com.agendamento.servicos_tecnicos.dto.AppointmentDTO;
import com.agendamento.servicos_tecnicos.dto.AppointmentPatchDTO;
import com.agendamento.servicos_tecnicos.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentDTO> create(@Valid @RequestBody AppointmentCreateDTO dto) {
        AppointmentDTO created = appointmentService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getById(@PathVariable Long id) {
        AppointmentDTO appointment = appointmentService.getById(id);
        return ResponseEntity.ok(appointment);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> list(@RequestParam(value = "status", required = false) String status,
                                                      @RequestParam(value = "startDate", required = false) String startDate,
                                                      @RequestParam(value = "clientId", required = false) Long clientId,
                                                      @RequestParam(value = "stylistId", required = false) Long stylistId,
                                                      @RequestParam(value = "page", defaultValue = "1") int page,
                                                      @RequestParam(value = "limit", defaultValue = "20") int limit) {
        List<AppointmentDTO> appointments = appointmentService.list(status, startDate, clientId, stylistId, page, limit);
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> update(@PathVariable Long id,
                                                 @Valid @RequestBody AppointmentCreateDTO dto) {
        AppointmentDTO updated = appointmentService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AppointmentDTO> patch(@PathVariable Long id,
                                                @Valid @RequestBody AppointmentPatchDTO dto) {
        AppointmentDTO patched = appointmentService.patch(id, dto);
        return ResponseEntity.ok(patched);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        appointmentService.delete(id);
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<AppointmentDTO> confirm(@PathVariable Long id) {
        AppointmentDTO confirmed = appointmentService.confirm(id);
        return ResponseEntity.ok(confirmed);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<AppointmentDTO> cancel(@PathVariable Long id) {
        AppointmentDTO cancelled = appointmentService.cancel(id);
        return ResponseEntity.ok(cancelled);
    }

    @PatchMapping("/{id}/reschedule")
    public ResponseEntity<AppointmentDTO> reschedule(@PathVariable Long id,
                                                     @RequestBody LocalDateTime newDate) {
        AppointmentDTO rescheduled = appointmentService.reschedule(id, newDate);
        return ResponseEntity.ok(rescheduled);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<AppointmentDTO> complete(@PathVariable Long id) {
        AppointmentDTO completed = appointmentService.complete(id);
        return ResponseEntity.ok(completed);
    }

    @GetMapping("/availability")
    public ResponseEntity<List<LocalDateTime>> getAvailability(@RequestParam(value = "stylistId", required = false) Long stylistId,
                                                               @RequestParam(value = "serviceIds", required = false) List<Long> serviceIds,
                                                               @RequestParam(value = "date") String date) {
        List<LocalDateTime> slots = appointmentService.getAvailability(stylistId, serviceIds, date);
        return ResponseEntity.ok(slots);
    }
}
