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
@RequestMapping("/servicos")
@RequiredArgsConstructor
public class ServicoController {

    private final ServicoService servicoService;

    @PostMapping
    public ResponseEntity<ServicoDTO> criar(@Valid @RequestBody ServicoDTO dto) {
        ServicoDTO criado = servicoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoDTO> buscarPorId(@PathVariable Long id) {
        ServicoDTO servico = servicoService.buscarPorId(id);
        return ResponseEntity.ok(servico);
    }

    @GetMapping
    public ResponseEntity<List<ServicoDTO>> listarTodos() {
        List<ServicoDTO> servicos = servicoService.listarTodos();
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ServicoDTO>> buscarPorNome(@RequestParam("nome") String nome) {
        List<ServicoDTO> servicos = servicoService.buscarPorNome(nome);
        return ResponseEntity.ok(servicos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoDTO> atualizar(@PathVariable Long id,
                                                @Valid @RequestBody ServicoDTO dto) {
        ServicoDTO atualizado = servicoService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        servicoService.deletar(id);
    }
}
