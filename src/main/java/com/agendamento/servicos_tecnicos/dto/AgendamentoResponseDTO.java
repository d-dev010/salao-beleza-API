package com.agendamento.servicos_tecnicos.dto;

import java.time.LocalDateTime;

public class AgendamentoResponseDTO {
    private Long id;
    private LocalDateTime dataHora;
    private String nomeCliente;
    private Long usuarioId;
    private String nomeUsuario;
    private String emailUsuario;
    private Long servicoId;
    private String nomeServico;
    private Integer duracaoServico;
    private String status;

    public AgendamentoResponseDTO() {
    }

    public AgendamentoResponseDTO(Long id,
                                  LocalDateTime dataHora,
                                  String nomeCliente,
                                  Long usuarioId,
                                  String nomeUsuario,
                                  String emailUsuario,
                                  Long servicoId,
                                  String nomeServico,
                                  Integer duracaoServico,
                                  String status) {
        this.id = id;
        this.dataHora = dataHora;
        this.nomeCliente = nomeCliente;
        this.usuarioId = usuarioId;
        this.nomeUsuario = nomeUsuario;
        this.emailUsuario = emailUsuario;
        this.servicoId = servicoId;
        this.nomeServico = nomeServico;
        this.duracaoServico = duracaoServico;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
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

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public Long getServicoId() {
        return servicoId;
    }

    public void setServicoId(Long servicoId) {
        this.servicoId = servicoId;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public Integer getDuracaoServico() {
        return duracaoServico;
    }

    public void setDuracaoServico(Integer duracaoServico) {
        this.duracaoServico = duracaoServico;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private LocalDateTime dataHora;
        private String nomeCliente;
        private Long usuarioId;
        private String nomeUsuario;
        private String emailUsuario;
        private Long servicoId;
        private String nomeServico;
        private Integer duracaoServico;
        private String status;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder dataHora(LocalDateTime dataHora) {
            this.dataHora = dataHora;
            return this;
        }

        public Builder nomeCliente(String nomeCliente) {
            this.nomeCliente = nomeCliente;
            return this;
        }

        public Builder usuarioId(Long usuarioId) {
            this.usuarioId = usuarioId;
            return this;
        }

        public Builder nomeUsuario(String nomeUsuario) {
            this.nomeUsuario = nomeUsuario;
            return this;
        }

        public Builder emailUsuario(String emailUsuario) {
            this.emailUsuario = emailUsuario;
            return this;
        }

        public Builder servicoId(Long servicoId) {
            this.servicoId = servicoId;
            return this;
        }

        public Builder nomeServico(String nomeServico) {
            this.nomeServico = nomeServico;
            return this;
        }

        public Builder duracaoServico(Integer duracaoServico) {
            this.duracaoServico = duracaoServico;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public AgendamentoResponseDTO build() {
            return new AgendamentoResponseDTO(
                    id,
                    dataHora,
                    nomeCliente,
                    usuarioId,
                    nomeUsuario,
                    emailUsuario,
                    servicoId,
                    nomeServico,
                    duracaoServico,
                    status
            );
        }
    }
}