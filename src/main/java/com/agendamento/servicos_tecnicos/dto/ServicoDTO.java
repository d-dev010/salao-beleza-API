package com.agendamento.servicos_tecnicos.dto;

import jakarta.validation.constraints.*;

public class ServicoDTO {
    private Long id;

    @NotBlank(message = "Nome do serviço é obrigatório")
    @Size(
            min = 3,
            max = 100,
            message = "Nome deve ter entre 3 e 100 caracteres"
    )
    private String nome;

    @NotNull(message = "Duração é obrigatória")
    @Min(
            value = 15,
            message = "Duração mínima é 15 minutos"
    )
    @Max(
            value = 480,
            message = "Duração máxima é 8 horas (480 minutos)"
    )
    private Integer duracao;

    @Size(
            max = 500,
            message = "Descrição pode ter no máximo 500 caracteres"
    )
    private String descricao;

    public ServicoDTO() {
    }

    public ServicoDTO(Long id, String nome, Integer duracao, String descricao) {
        this.id = id;
        this.nome = nome;
        this.duracao = duracao;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String nome;
        private Integer duracao;
        private String descricao;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder duracao(Integer duracao) {
            this.duracao = duracao;
            return this;
        }

        public Builder descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public ServicoDTO build() {
            return new ServicoDTO(id, nome, duracao, descricao);
        }
    }
}