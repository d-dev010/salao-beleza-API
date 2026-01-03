package com.agendamento.servicos_tecnicos.repository;

import com.agendamento.servicos_tecnicos.entity.Agendamento;
import com.agendamento.servicos_tecnicos.entity.Agendamento.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByUsuarioId(Long usuarioId);
    List<Agendamento> findByStatus(Status status);
    List<Agendamento> findByUsuarioIdAndStatus(Long usuarioId, Status status);
    List<Agendamento> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Agendamento> findByDataHoraAfter(LocalDateTime dataHora);
    List<Agendamento> findByDataHoraBefore(LocalDateTime dataHora);
    List<Agendamento> findByServicoId(Long servicoId);
    List<Agendamento> findAllByOrderByDataHoraAsc();
    @Query("SELECT a FROM Agendamento a " +
            "WHERE a.usuario.id = :usuarioId " +
            "AND a.dataHora BETWEEN :inicio AND :fim " +
            "AND a.status = 'AGENDADO'")
    List<Agendamento> findConflitos(
            @Param("usuarioId") Long usuarioId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );
    @Query("SELECT a FROM Agendamento a " +
            "JOIN FETCH a.usuario u " +
            "JOIN FETCH a.servico s " +
            "WHERE a.usuario.id = :usuarioId")
    List<Agendamento> findByUsuarioIdComDetalhes(@Param("usuarioId") Long usuarioId);
    @Query(value = "SELECT * FROM agendamentos " +
            "WHERE usuario_id = :usuarioId " +
            "AND DATE(data_hora) = :data",
            nativeQuery = true)
    List<Agendamento> findByUsuarioAndData(
            @Param("usuarioId") Long usuarioId,
            @Param("data") String data
    );
    @Query("SELECT COUNT(a) FROM Agendamento a " +
            "WHERE a.usuario.id = :usuarioId " +
            "AND a.status = :status")
    Long countByUsuarioAndStatus(
            @Param("usuarioId") Long usuarioId,
            @Param("status") Status status
    );

}