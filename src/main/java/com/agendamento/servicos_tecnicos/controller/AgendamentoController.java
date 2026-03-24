package com.agendamento.servicos_tecnicos.controller;

import com.agendamento.servicos_tecnicos.dto.AgendamentoPatchDTO;
import com.agendamento.servicos_tecnicos.dto.AgendamentoRequestDTO;
import com.agendamento.servicos_tecnicos.dto.AgendamentoResponseDTO;
import com.agendamento.servicos_tecnicos.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> criar(@Valid @RequestBody AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO criado = appointmentService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> listar(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "usuarioId", required = false) Long usuarioId
    ) {
        if (status != null && !status.isBlank()) {
            return ResponseEntity.ok(appointmentService.listarPorStatus(status));
        }
        if (usuarioId != null) {
            return ResponseEntity.ok(appointmentService.listarPorUsuario(usuarioId));
        }
        return ResponseEntity.ok(appointmentService.listarTodos());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> atualizarStatus(@PathVariable Long id,
                                                                  @Valid @RequestBody AgendamentoPatchDTO dto) {
        AgendamentoResponseDTO atualizado = appointmentService.atualizarStatus(id, dto.status());
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        appointmentService.deletar(id);
    }
}
