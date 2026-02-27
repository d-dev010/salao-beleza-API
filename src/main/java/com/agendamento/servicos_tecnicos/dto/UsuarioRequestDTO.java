package com.agendamento.servicos_tecnicos.dto;

import jakarta.validation.constraints.*;

public class UsuarioRequestDTO {
    @NotBlank(message = "Nome é obrigatório")
    @Size(
            min = 3,
            message = "Nome deve ter entre 3 e 100 caracteres"
    )
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(
            min = 6,
            message = "Senha deve ter no mínimo 6 caracteres"
    )
    private String senha;

    @NotNull(message = "Tipo de usuário é obrigatório")
    @Pattern(
            regexp = "CLIENTE|CABELEREIRO|ADMIN",
            message = "Tipo deve ser CLIENTE, CABELEREIRO ou ADMIN"
    )
    private String role;

    public UsuarioRequestDTO() {
    }

    public UsuarioRequestDTO(String nome, String email, String senha, String role) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.role = role;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}