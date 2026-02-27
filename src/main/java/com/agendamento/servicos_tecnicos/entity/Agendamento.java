package com.agendamento.servicos_tecnicos.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "nome_cliente", length = 150)
    private String nomeCliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.AGENDADO;

    public Agendamento() {
    }

    public Agendamento(Long id, LocalDateTime dataHora, String nomeCliente, Usuario usuario, Servico servico, Status status) {
        this.id = id;
        this.dataHora = dataHora;
        this.nomeCliente = nomeCliente;
        this.usuario = usuario;
        this.servico = servico;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private LocalDateTime dataHora;
        private String nomeCliente;
        private Usuario usuario;
        private Servico servico;
        private Status status = Status.AGENDADO;

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

        public Builder usuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public Builder servico(Servico servico) {
            this.servico = servico;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Agendamento build() {
            return new Agendamento(id, dataHora, nomeCliente, usuario, servico, status);
        }
    }

    public enum Status {
        AGENDADO,
        CONCLUIDO,
        CANCELADO
    }
}