package com.agendamento.servicos_tecnicos.controller;

import com.agendamento.servicos_tecnicos.dto.AgendamentoRequestDTO;
import com.agendamento.servicos_tecnicos.dto.AgendamentoResponseDTO;
import com.agendamento.servicos_tecnicos.service.AgendamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> criar(@Valid @RequestBody AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO criado = agendamentoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        AgendamentoResponseDTO agendamento = agendamentoService.buscarPorId(id);
        return ResponseEntity.ok(agendamento);
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> listarTodos() {
        List<AgendamentoResponseDTO> agendamentos = agendamentoService.listarTodos();
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AgendamentoResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<AgendamentoResponseDTO> agendamentos = agendamentoService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/status")
    public ResponseEntity<List<AgendamentoResponseDTO>> listarPorStatus(@RequestParam("status") String status) {
        List<AgendamentoResponseDTO> agendamentos = agendamentoService.listarPorStatus(status);
        return ResponseEntity.ok(agendamentos);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AgendamentoResponseDTO> atualizarStatus(@PathVariable Long id,
                                                                  @RequestParam("status") String novoStatus) {
        AgendamentoResponseDTO atualizado = agendamentoService.atualizarStatus(id, novoStatus);
        return ResponseEntity.ok(atualizado);
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<AgendamentoResponseDTO> cancelar(@PathVariable Long id) {
        AgendamentoResponseDTO cancelado = agendamentoService.cancelar(id);
        return ResponseEntity.ok(cancelado);
    }

    @PostMapping("/{id}/concluir")
    public ResponseEntity<AgendamentoResponseDTO> concluir(@PathVariable Long id) {
        AgendamentoResponseDTO concluido = agendamentoService.concluir(id);
        return ResponseEntity.ok(concluido);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        agendamentoService.deletar(id);
    }
}
