package com.agendamento.servicos_tecnicos.repository;

import com.agendamento.servicos_tecnicos.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
    List<Servico> findByDuracaoLessThanEqual(Integer duracao);
    List<Servico> findAllByOrderByDuracaoAsc();
    List<Servico> findAllByOrderByNomeAsc();
    long countByDuracao(Integer duracao);

    // Usado por ServicoService para validação de nome único
    boolean existsByNome(String nome);

    // Usado por ServicoService para busca por nome (contém, case-insensitive)
    List<Servico> findByNomeContainingIgnoreCase(String nome);
}