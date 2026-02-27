package com.agendamento.servicos_tecnicos.dto;

import jakarta.validation.constraints.*;

public class AgendamentoRequestDTO {
    @NotBlank(message = "Data e hora são obrigatórios")
    private String dataHora;

    @Size(max = 150, message = "Nome do cliente pode ter no máximo 150 caracteres")
    private String nomeCliente;

    @Positive(message = "ID do usuário deve ser positivo")
    private Long usuarioId;

    @NotNull(message = "ID do serviço é obrigatório")
    @Positive(message = "ID do serviço deve ser positivo")
    private Long servicoId;

    public AgendamentoRequestDTO() {
    }

    public AgendamentoRequestDTO(String dataHora, String nomeCliente, Long usuarioId, Long servicoId) {
        this.dataHora = dataHora;
        this.nomeCliente = nomeCliente;
        this.usuarioId = usuarioId;
        this.servicoId = servicoId;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getServicoId() {
        return servicoId;
    }

    public void setServicoId(Long servicoId) {
        this.servicoId = servicoId;
    }
}