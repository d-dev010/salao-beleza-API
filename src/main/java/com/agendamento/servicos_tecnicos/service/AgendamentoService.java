package com.agendamento.servicos_tecnicos.service;

import com.agendamento.servicos_tecnicos.dto.*;
import com.agendamento.servicos_tecnicos.entity.*;
import com.agendamento.servicos_tecnicos.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final UsuarioService usuarioService;
    private final ServicoService servicoService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    public AgendamentoResponseDTO criar(AgendamentoRequestDTO dto) {
        LocalDateTime dataHora;

        try {
            dataHora = LocalDateTime.parse(dto.getDataHora(), formatter);

        } catch (DateTimeParseException e) {
            throw new RuntimeException("Formato de data/hora inválido. Use: YYYY-MM-DDTHH:mm:ss");
        }

        if (dataHora.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Não é possível agendar no passado!");
        }


        Usuario usuario = usuarioService.buscarEntidadePorId(dto.getUsuarioId());

        if (usuario.getRole() != Usuario.Role.TECNICO) {
            throw new RuntimeException("Apenas técnicos podem ter agendamentos. " +
                    "Usuário " + usuario.getNome() + " é " + usuario.getRole());
        }


        Servico servico = servicoService.buscarEntidadePorId(dto.getServicoId());


        LocalDateTime dataHoraFim = dataHora.plusMinutes(servico.getDuracao());

        List<Agendamento> conflitos = agendamentoRepository.findConflitos(
                usuario.getId(),
                dataHora,
                dataHoraFim
        );

        if (!conflitos.isEmpty()) {
            Agendamento conflito = conflitos.get(0);

            throw new RuntimeException(
                    String.format(
                            "Conflito de horário! Técnico %s já tem agendamento às %s",
                            usuario.getNome(),
                            conflito.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                    )
            );
        }



        Agendamento agendamento = Agendamento.builder()
                .dataHora(dataHora)
                .usuario(usuario)
                .servico(servico)
                .status(Agendamento.Status.AGENDADO)  // Status inicial
                .build();

        agendamento = agendamentoRepository.save(agendamento);

        return toResponseDTO(agendamento);
    }




    public AgendamentoResponseDTO buscarPorId(Long id) {

        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado com ID: " + id));

        return toResponseDTO(agendamento);
    }


    public List<AgendamentoResponseDTO> listarTodos() {

        return agendamentoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }



    public List<AgendamentoResponseDTO> listarPorUsuario(Long usuarioId) {

        // Verificar se usuário existe
        usuarioService.buscarEntidadePorId(usuarioId);

        return agendamentoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }



    public List<AgendamentoResponseDTO> listarPorStatus(String statusStr) {

        Agendamento.Status status;
        try {
            status = Agendamento.Status.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Status inválido: " + statusStr +
                    ". Use: AGENDADO, CONCLUIDO ou CANCELADO");
        }

        return agendamentoRepository.findByStatus(status)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }


    public AgendamentoResponseDTO atualizarStatus(Long id, String novoStatusStr) {

        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado com ID: " + id));

        Agendamento.Status novoStatus;
        try {
            novoStatus = Agendamento.Status.valueOf(novoStatusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Status inválido: " + novoStatusStr);
        }

        if (agendamento.getStatus() == Agendamento.Status.CANCELADO) {
            throw new RuntimeException("Não é possível alterar agendamento cancelado!");
        }

        if (agendamento.getStatus() == Agendamento.Status.CONCLUIDO) {
            throw new RuntimeException("Não é possível alterar agendamento concluído!");
        }

        agendamento.setStatus(novoStatus);

        agendamento = agendamentoRepository.save(agendamento);

        return toResponseDTO(agendamento);
    }


    public AgendamentoResponseDTO cancelar(Long id) {

        return atualizarStatus(id, "CANCELADO");
    }


    public AgendamentoResponseDTO concluir(Long id) {

        return atualizarStatus(id, "CONCLUIDO");
    }


    public void deletar(Long id) {

        if (!agendamentoRepository.existsById(id)) {
            throw new RuntimeException("Agendamento não encontrado com ID: " + id);
        }

        agendamentoRepository.deleteById(id);
    }


    private AgendamentoResponseDTO toResponseDTO(Agendamento agendamento) {

        return AgendamentoResponseDTO.builder()
                .id(agendamento.getId())
                .dataHora(agendamento.getDataHora())
                .usuarioId(agendamento.getUsuario().getId())
                .nomeUsuario(agendamento.getUsuario().getNome())
                .emailUsuario(agendamento.getUsuario().getEmail())
                .servicoId(agendamento.getServico().getId())
                .nomeServico(agendamento.getServico().getNome())
                .duracaoServico(agendamento.getServico().getDuracao())
                .status(agendamento.getStatus().name())
                .build();
    }
