package com.agendamento.servicos_tecnicos.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
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
}