package com.agendamento.servicos_tecnicos.service;

import com.agendamento.servicos_tecnicos.dto.ServicoDTO;
import com.agendamento.servicos_tecnicos.entity.Servico;
import com.agendamento.servicos_tecnicos.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ServicoRepository servicoRepository;


    // ==========================================
    // MÉTODO: CRIAR SERVIÇO
    // ==========================================

    public ServicoDTO criar(ServicoDTO dto) {

        // VALIDAÇÃO: Verificar se já existe serviço com mesmo nome
        if (servicoRepository.existsByNome(dto.getNome())) {
            throw new RuntimeException("Já existe um serviço com o nome: " + dto.getNome());
        }

        // CONVERSÃO: DTO → Entidade
        Servico servico = new Servico();
        servico.setNome(dto.getNome());
        servico.setDuracao(dto.getDuracao());
        servico.setDescricao(dto.getDescricao());

        // SALVAR
        servico = servicoRepository.save(servico);

        // CONVERSÃO: Entidade → DTO
        return toDTO(servico);
    }


    // ==========================================
    // MÉTODO: BUSCAR SERVIÇO POR ID
    // ==========================================

    public ServicoDTO buscarPorId(Long id) {

        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com ID: " + id));

        return toDTO(servico);
    }


    // ==========================================
    // MÉTODO: LISTAR TODOS OS SERVIÇOS
    // ==========================================

    public List<ServicoDTO> listarTodos() {

        return servicoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    // ==========================================
    // MÉTODO: BUSCAR SERVIÇOS POR NOME
    // ==========================================

    // Busca parcial, case-insensitive
    // Exemplo: buscar("manut") retorna "Manutenção de PC", "MANUTENÇÃO ELÉTRICA"
    public List<ServicoDTO> buscarPorNome(String nome) {

        return servicoRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    // ==========================================
    // MÉTODO: ATUALIZAR SERVIÇO
    // ==========================================

    public ServicoDTO atualizar(Long id, ServicoDTO dto) {

        // Buscar serviço existente
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com ID: " + id));

        // Verificar se novo nome já existe (em outro serviço)
        if (!servico.getNome().equals(dto.getNome())) {
            if (servicoRepository.existsByNome(dto.getNome())) {
                throw new RuntimeException("Já existe um serviço com o nome: " + dto.getNome());
            }
        }

        // Atualizar campos
        servico.setNome(dto.getNome());
        servico.setDuracao(dto.getDuracao());
        servico.setDescricao(dto.getDescricao());

        // Salvar
        servico = servicoRepository.save(servico);

        return toDTO(servico);
    }


    // ==========================================
    // MÉTODO: DELETAR SERVIÇO
    // ==========================================

    public void deletar(Long id) {

        if (!servicoRepository.existsById(id)) {
            throw new RuntimeException("Serviço não encontrado com ID: " + id);
        }

        // TODO: Verificar se há agendamentos usando este serviço
        // Regra de negócio: não permitir deletar serviço com agendamentos

        servicoRepository.deleteById(id);
    }


    // ==========================================
    // MÉTODO AUXILIAR: ENTIDADE → DTO
    // ==========================================

    private ServicoDTO toDTO(Servico servico) {

        return ServicoDTO.builder()
                .id(servico.getId())
                .nome(servico.getNome())
                .duracao(servico.getDuracao())
                .descricao(servico.getDescricao())
                .build();
    }


    // ==========================================
    // MÉTODO AUXILIAR: BUSCAR ENTIDADE POR ID
    // ==========================================

    // Usado por AgendamentoService
    public Servico buscarEntidadePorId(Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com ID: " + id));
    }
}