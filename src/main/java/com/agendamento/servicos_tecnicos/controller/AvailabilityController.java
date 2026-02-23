package com.agendamento.servicos_tecnicos.controller;

import com.agendamento.servicos_tecnicos.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAvailability(@RequestParam(value = "stylistId", required = false) Long stylistId,
                                                               @RequestParam(value = "date", required = false) String date) {
        Map<String, Object> availability = availabilityService.getAvailability(stylistId, date);
        return ResponseEntity.ok(availability);
    }

    @GetMapping("/slots")
    public ResponseEntity<List<LocalDateTime>> getSlots(@RequestParam(value = "stylistId", required = false) Long stylistId,
                                                        @RequestParam(value = "serviceIds", required = false) List<Long> serviceIds,
                                                        @RequestParam(value = "date") String date) {
        List<LocalDateTime> slots = availabilityService.getSlots(stylistId, serviceIds, date);
        return ResponseEntity.ok(slots);
    }

    @PostMapping("/block")
    public ResponseEntity<Void> block(@RequestBody Map<String, Object> blockData) {
        availabilityService.block(blockData);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/block/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unblock(@PathVariable Long id) {
        availabilityService.unblock(id);
    }
}
