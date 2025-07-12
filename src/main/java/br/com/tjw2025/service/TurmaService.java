package br.com.tjw2025.service;

import br.com.tjw2025.entity.Turma;
import br.com.tjw2025.entity.Disciplina;
import br.com.tjw2025.entity.Professor;

import br.com.tjw2025.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TurmaService {
    
    @Autowired
    private TurmaRepository turmaRepository;
    
    public Turma salvar(Turma turma) {
        if (turma.getId() != null) {
            if (turmaRepository.existsByCodigoExcludingId(turma.getCodigo(), turma.getId())) {
                throw new RuntimeException("Código já cadastrado para outra turma");
            }
        }
        
        // Validar se a turma tem vagas disponíveis
        if (turma.getVagasOcupadas() > turma.getVagas()) {
            throw new RuntimeException("Vagas ocupadas não podem ser maiores que o total de vagas");
        }
        
        return turmaRepository.save(turma);
    }
    
    @Transactional(readOnly = true)
    public Optional<Turma> buscarPorId(Long id) {
        return turmaRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public Optional<Turma> buscarPorCodigo(String codigo) {
        return turmaRepository.findByCodigo(codigo);
    }
    
    @Transactional(readOnly = true)
    public List<Turma> listarTodos() {
        return turmaRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Turma> listarAtivas() {
        return turmaRepository.findByAtivaTrue();
    }
    
    @Transactional(readOnly = true)
    public List<Turma> listarInativas() {
        return turmaRepository.findByAtivaFalse();
    }
    
    @Transactional(readOnly = true)
    public List<Turma> buscarPorDisciplina(Disciplina disciplina) {
        return turmaRepository.findByDisciplina(disciplina);
    }
    
    @Transactional(readOnly = true)
    public List<Turma> buscarPorProfessor(Professor professor) {
        return turmaRepository.findByProfessor(professor);
    }
    
    @Transactional(readOnly = true)
    public List<Turma> buscarPorDiaSemana(String diaSemana) {
        return turmaRepository.findByDiaSemanaIgnoreCase(diaSemana);
    }
    
    @Transactional(readOnly = true)
    public List<Turma> buscarPorSala(String sala) {
        return turmaRepository.findBySalaContainingIgnoreCase(sala);
    }
    
    @Transactional(readOnly = true)
    public List<Turma> listarComVagasDisponiveis() {
        return turmaRepository.findTurmasComVagasDisponiveis();
    }
    
    @Transactional(readOnly = true)
    public List<Turma> listarLotadas() {
        return turmaRepository.findTurmasLotadas();
    }
    
    @Transactional(readOnly = true)
    public List<Turma> buscarPorPeriodoData(LocalDate dataInicio, LocalDate dataFim) {
        return turmaRepository.findByPeriodoData(dataInicio, dataFim);
    }
    
    public Turma ativar(Long id) {
        Optional<Turma> turmaOpt = turmaRepository.findById(id);
        if (turmaOpt.isPresent()) {
            Turma turma = turmaOpt.get();
            turma.setAtiva(true);
            return turmaRepository.save(turma);
        }
        throw new RuntimeException("Turma não encontrada");
    }
    
    public Turma inativar(Long id) {
        Optional<Turma> turmaOpt = turmaRepository.findById(id);
        if (turmaOpt.isPresent()) {
            Turma turma = turmaOpt.get();
            turma.setAtiva(false);
            return turmaRepository.save(turma);
        }
        throw new RuntimeException("Turma não encontrada");
    }
    
    public void excluir(Long id) {
        Optional<Turma> turmaOpt = turmaRepository.findById(id);
        if (turmaOpt.isPresent()) {
            Turma turma = turmaOpt.get();
            turma.setAtiva(false);
            turmaRepository.save(turma);
        } else {
            throw new RuntimeException("Turma não encontrada");
        }
    }
    
    @Transactional(readOnly = true)
    public long contarAtivas() {
        return turmaRepository.countByAtivaTrue();
    }
    
    @Transactional(readOnly = true)
    public long contarInativas() {
        return turmaRepository.countByAtivaFalse();
    }
    
    // Método para incrementar vagas ocupadas (quando aluno se matricula)
    public void incrementarVagasOcupadas(Long turmaId) {
        Optional<Turma> turmaOpt = turmaRepository.findById(turmaId);
        if (turmaOpt.isPresent()) {
            Turma turma = turmaOpt.get();
            if (turma.temVagasDisponiveis()) {
                turma.setVagasOcupadas(turma.getVagasOcupadas() + 1);
                turmaRepository.save(turma);
            } else {
                throw new RuntimeException("Turma não possui vagas disponíveis");
            }
        } else {
            throw new RuntimeException("Turma não encontrada");
        }
    }
    
    // Método para decrementar vagas ocupadas (quando aluno cancela matrícula)
    public void decrementarVagasOcupadas(Long turmaId) {
        Optional<Turma> turmaOpt = turmaRepository.findById(turmaId);
        if (turmaOpt.isPresent()) {
            Turma turma = turmaOpt.get();
            if (turma.getVagasOcupadas() > 0) {
                turma.setVagasOcupadas(turma.getVagasOcupadas() - 1);
                turmaRepository.save(turma);
            }
        } else {
            throw new RuntimeException("Turma não encontrada");
        }
    }
} 