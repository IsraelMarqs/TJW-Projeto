package br.com.tjw2025.service;

import br.com.tjw2025.entity.Professor;
import br.com.tjw2025.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProfessorService extends BaseService<Professor, Long> {
    
    private final ProfessorRepository professorRepository;
    
    @Autowired
    public ProfessorService(ProfessorRepository professorRepository) {
        super(professorRepository);
        this.professorRepository = professorRepository;
    }
    
    // Salvar ou atualizar professor
    @Override
    public Professor salvar(Professor professor) {
        // Validações de negócio
        if (professor.getId() == null) {
            // Novo professor - definir data de contratação se não informada
            if (professor.getDataContratacao() == null) {
                professor.setDataContratacao(LocalDate.now());
            }
        } else {
            // Professor existente - verificar se email/CPF já existem para outros professores
            if (professorRepository.existsByEmailExcludingId(professor.getEmail(), professor.getId())) {
                throw new RuntimeException("Email já cadastrado para outro professor");
            }
            if (professorRepository.existsByCpfExcludingId(professor.getCpf(), professor.getId())) {
                throw new RuntimeException("CPF já cadastrado para outro professor");
            }
        }
        
        return super.salvar(professor);
    }
    
    // Buscar por área de atuação
    @Transactional(readOnly = true)
    public List<Professor> buscarPorAreaAtuacao(String areaAtuacao) {
        return professorRepository.findByAreaAtuacaoContainingIgnoreCase(areaAtuacao);
    }
    
    // Listar professores ativos
    @Transactional(readOnly = true)
    public List<Professor> listarAtivos() {
        return professorRepository.findByAtivoTrue();
    }
    
    // Buscar por titulação
    @Transactional(readOnly = true)
    public List<Professor> buscarPorTitulacao(String titulacao) {
        return professorRepository.findByTitulacaoContainingIgnoreCase(titulacao);
    }
    
    // Excluir professor permanentemente
    public void excluirPermanentemente(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Professor não encontrado");
        }
        repository.deleteById(id);
    }
    
    // Listar áreas de atuação distintas
    @Transactional(readOnly = true)
    public List<String> listarAreasAtuacao() {
        return professorRepository.findDistinctAreaAtuacao();
    }
    
    // Listar titulações distintas
    @Transactional(readOnly = true)
    public List<String> listarTitulacoes() {
        return professorRepository.findDistinctTitulacao();
    }
} 