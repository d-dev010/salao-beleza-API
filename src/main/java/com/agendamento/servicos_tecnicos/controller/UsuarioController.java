package com.agendamento.servicos_tecnicos.controller;

import com.agendamento.servicos_tecnicos.dto.ClientDTO;
import com.agendamento.servicos_tecnicos.dto.ClientCreateDTO;
import com.agendamento.servicos_tecnicos.dto.AppointmentResponseDTO;
import com.agendamento.servicos_tecnicos.service.ClientService;
import com.agendamento.servicos_tecnicos.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<ClientDTO> create(@Valid @RequestBody ClientCreateDTO dto) {
        ClientDTO created = clientService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getById(@PathVariable Long id) {
        ClientDTO client = clientService.getById(id);
        return ResponseEntity.ok(client);
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> list(@RequestParam(value = "page", defaultValue = "1") int page,
                                                @RequestParam(value = "limit", defaultValue = "20") int limit) {
        List<ClientDTO> clients = clientService.list(page, limit);
        return ResponseEntity.ok(clients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> update(@PathVariable Long id,
                                            @Valid @RequestBody ClientCreateDTO dto) {
        ClientDTO updated = clientService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientDTO> patch(@PathVariable Long id,
                                           @RequestBody ClientCreateDTO dto) {
        ClientDTO patched = clientService.patch(id, dto);
        return ResponseEntity.ok(patched);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }

    @GetMapping("/{clientId}/appointments")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointments(@PathVariable Long clientId,
                                                                         @RequestParam(value = "status", required = false) String status) {
        List<AppointmentResponseDTO> appointments = appointmentService.listByClient(clientId, status);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{clientId}/appointment-history")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentHistory(@PathVariable Long clientId,
                                                                               @RequestParam(value = "page", defaultValue = "1") int page) {
        List<AppointmentResponseDTO> history = appointmentService.getHistory(clientId, page);
        return ResponseEntity.ok(history);
    }
}
