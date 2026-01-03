package com.agendamento.servicos_tecnicos;

import com.agendamento.servicos_tecnicos.entity.*;
import com.agendamento.servicos_tecnicos.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

// Esta classe executa automaticamente quando a aplicação iniciar
@Component
@RequiredArgsConstructor
public class TestRepositories implements CommandLineRunner {

    // Spring injeta automaticamente os repositories
    private final UsuarioRepository usuarioRepository;
    private final ServicoRepository servicoRepository;
    private final AgendamentoRepository agendamentoRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n===== TESTANDO REPOSITORIES =====\n");

        // 1. Criar e salvar usuário (cabeleireiro)
        Usuario cabeleireiro = usuarioRepository.findByEmail("joao@email.com")
                .orElseGet(() -> {
                    Usuario novo = Usuario.builder()
                            .nome("João Silva")
                            .email("joao@email.com")
                            .senha("123456")
                            .role(Usuario.Role.CABELEREIRO)
                            .build();
                    return usuarioRepository.save(novo);
                });

        if (cabeleireiro.getRole() != Usuario.Role.CABELEREIRO) {
            cabeleireiro.setRole(Usuario.Role.CABELEREIRO);
            cabeleireiro = usuarioRepository.save(cabeleireiro);
        }

        System.out.println("✅ Cabeleireiro: ID = " + cabeleireiro.getId());

        // 2. Criar e salvar serviço
        Servico servico = Servico.builder()
                .nome("Instalação de Ar Condicionado")
                .duracao(120)
                .descricao("Instalação completa incluindo suporte")
                .build();

        servico = servicoRepository.save(servico);
        System.out.println("✅ Serviço criado: ID = " + servico.getId());

        // 3. Criar e salvar agendamento
        Agendamento agendamento = Agendamento.builder()
                .dataHora(LocalDateTime.now().plusDays(1))
                .usuario(cabeleireiro)
                .servico(servico)
                .status(Agendamento.Status.AGENDADO)
                .build();

        agendamento = agendamentoRepository.save(agendamento);
        System.out.println("✅ Agendamento criado: ID = " + agendamento.getId());

        // 4. Buscar dados
        long totalUsuarios = usuarioRepository.count();
        long totalServicos = servicoRepository.count();
        long totalAgendamentos = agendamentoRepository.count();

        System.out.println("\n📊 ESTATÍSTICAS:");
        System.out.println("Total de usuários: " + totalUsuarios);
        System.out.println("Total de serviços: " + totalServicos);
        System.out.println("Total de agendamentos: " + totalAgendamentos);

        System.out.println("\n===== TESTES CONCLUÍDOS =====\n");
    }
}