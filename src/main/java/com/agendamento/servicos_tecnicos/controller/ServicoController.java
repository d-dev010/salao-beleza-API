package com.agendamento.servicos_tecnicos.controller;

import com.agendamento.servicos_tecnicos.dto.ServicoDTO;
import com.agendamento.servicos_tecnicos.service.ServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/servicos")
@RequiredArgsConstructor
public class ServicoController {

    private final ServicoService servicoService;

    @PostMapping
    public ResponseEntity<ServicoDTO> criar(@Valid @RequestBody ServicoDTO dto) {
        ServicoDTO created = servicoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoDTO> buscarPorId(@PathVariable Long id) {
        ServicoDTO servico = servicoService.buscarPorId(id);
        return ResponseEntity.ok(servico);
    }

    @GetMapping
    public ResponseEntity<List<ServicoDTO>> listar(
            @RequestParam(value = "nome", required = false) String nome
    ) {
        List<ServicoDTO> servicos;
        if (nome != null && !nome.isBlank()) {
            servicos = servicoService.buscarPorNome(nome);
        } else {
            servicos = servicoService.listarTodos();
        }
        return ResponseEntity.ok(servicos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoDTO> atualizar(@PathVariable Long id,
                                                @Valid @RequestBody ServicoDTO dto) {
        ServicoDTO updated = servicoService.atualizar(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        servicoService.deletar(id);
    }
}
