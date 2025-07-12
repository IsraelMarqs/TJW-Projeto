package br.com.tjw2025.service;

import br.com.tjw2025.entity.Matricula;
import br.com.tjw2025.entity.Aluno;
import br.com.tjw2025.entity.Turma;
import br.com.tjw2025.repository.MatriculaRepository;
import br.com.tjw2025.repository.AlunoRepository;
import br.com.tjw2025.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MatriculaService {
    
    @Autowired
    private MatriculaRepository matriculaRepository;
    
    @Autowired
    private TurmaService turmaService;
    
    @Autowired
    private AlunoRepository alunoRepository;
    
    @Autowired
    private TurmaRepository turmaRepository;
    
    public Matricula salvar(Matricula matricula) {
        if (matricula.getAluno() == null || matricula.getAluno().getId() == null) {
            throw new RuntimeException("Aluno é obrigatório");
        }
        if (matricula.getTurma() == null || matricula.getTurma().getId() == null) {
            throw new RuntimeException("Turma é obrigatória");
        }
        
        // Buscar aluno e turma do banco pelo ID
        Aluno aluno = alunoRepository.findById(matricula.getAluno().getId())
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        Turma turma = turmaRepository.findById(matricula.getTurma().getId())
            .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
        
        // Validações de negócio
        if (matricula.getId() == null) {
            // Nova matrícula
            if (matricula.getDataMatricula() == null) {
                matricula.setDataMatricula(LocalDate.now());
            }
            
            // Verificar se aluno já está matriculado na turma
            if (matriculaRepository.existsByAlunoAndTurmaAtiva(aluno, turma)) {
                throw new RuntimeException("Aluno já está matriculado nesta turma");
            }
            
            // Verificar se a turma tem vagas disponíveis
            if (!turma.temVagasDisponiveis()) {
                throw new RuntimeException("Turma não possui vagas disponíveis");
            }
            
            // Incrementar vagas ocupadas na turma
            turmaService.incrementarVagasOcupadas(turma.getId());
        }
        
        // Preencher os objetos completos na matrícula
        matricula.setAluno(aluno);
        matricula.setTurma(turma);
        return matriculaRepository.save(matricula);
    }
    
    @Transactional(readOnly = true)
    public Optional<Matricula> buscarPorId(Long id) {
        return matriculaRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Matricula> listarTodos() {
        return matriculaRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Matricula> listarAtivas() {
        return matriculaRepository.findByStatus(Matricula.StatusMatricula.ATIVA);
    }
    
    @Transactional(readOnly = true)
    public List<Matricula> listarCanceladas() {
        return matriculaRepository.findByStatus(Matricula.StatusMatricula.CANCELADA);
    }
    
    @Transactional(readOnly = true)
    public List<Matricula> listarTrancadas() {
        return matriculaRepository.findByStatus(Matricula.StatusMatricula.TRANCADA);
    }
    
    @Transactional(readOnly = true)
    public List<Matricula> listarConcluidas() {
        return matriculaRepository.findByStatus(Matricula.StatusMatricula.CONCLUIDA);
    }
    
    @Transactional(readOnly = true)
    public List<Matricula> buscarPorAluno(Aluno aluno) {
        return matriculaRepository.findByAluno(aluno);
    }
    
    @Transactional(readOnly = true)
    public List<Matricula> buscarPorTurma(Turma turma) {
        return matriculaRepository.findByTurma(turma);
    }
    
    @Transactional(readOnly = true)
    public Optional<Matricula> buscarPorAlunoETurma(Aluno aluno, Turma turma) {
        return matriculaRepository.findByAlunoAndTurma(aluno, turma);
    }
    
    @Transactional(readOnly = true)
    public List<Matricula> buscarPorStatus(Matricula.StatusMatricula status) {
        return matriculaRepository.findByStatus(status);
    }
    
    @Transactional(readOnly = true)
    public List<Matricula> buscarPorData(LocalDate data) {
        return matriculaRepository.findByDataMatricula(data);
    }
    
    @Transactional(readOnly = true)
    public List<Matricula> buscarPorPeriodoData(LocalDate dataInicio, LocalDate dataFim) {
        return matriculaRepository.findByPeriodoData(dataInicio, dataFim);
    }
    
    @Transactional(readOnly = true)
    public List<Matricula> buscarMatriculasAtivasPorAluno(Aluno aluno) {
        return matriculaRepository.findMatriculasAtivasByAluno(aluno);
    }
    
    @Transactional(readOnly = true)
    public List<Matricula> buscarMatriculasAtivasPorTurma(Turma turma) {
        return matriculaRepository.findMatriculasAtivasByTurma(turma);
    }
    
    @Transactional(readOnly = true)
    public List<Matricula> buscarHistoricoPorAluno(Aluno aluno) {
        return matriculaRepository.findHistoricoByAluno(aluno);
    }
    

    
    public Matricula cancelar(Long id) {
        Optional<Matricula> matriculaOpt = matriculaRepository.findById(id);
        if (matriculaOpt.isPresent()) {
            Matricula matricula = matriculaOpt.get();
            
            // Só pode cancelar matrículas ativas
            if (matricula.isAtiva()) {
                matricula.cancelar();
                
                // Decrementar vagas ocupadas na turma
                turmaService.decrementarVagasOcupadas(matricula.getTurma().getId());
                
                return matriculaRepository.save(matricula);
            } else {
                throw new RuntimeException("Só é possível cancelar matrículas ativas");
            }
        }
        throw new RuntimeException("Matrícula não encontrada");
    }
    
    public Matricula trancar(Long id) {
        Optional<Matricula> matriculaOpt = matriculaRepository.findById(id);
        if (matriculaOpt.isPresent()) {
            Matricula matricula = matriculaOpt.get();
            
            // Só pode trancar matrículas ativas
            if (matricula.isAtiva()) {
                matricula.trancar();
                return matriculaRepository.save(matricula);
            } else {
                throw new RuntimeException("Só é possível trancar matrículas ativas");
            }
        }
        throw new RuntimeException("Matrícula não encontrada");
    }
    
    public Matricula concluir(Long id) {
        Optional<Matricula> matriculaOpt = matriculaRepository.findById(id);
        if (matriculaOpt.isPresent()) {
            Matricula matricula = matriculaOpt.get();
            
            // Só pode concluir matrículas ativas
            if (matricula.isAtiva()) {
                matricula.concluir();
                return matriculaRepository.save(matricula);
            } else {
                throw new RuntimeException("Só é possível concluir matrículas ativas");
            }
        }
        throw new RuntimeException("Matrícula não encontrada");
    }
    
    public Matricula reativar(Long id) {
        Optional<Matricula> matriculaOpt = matriculaRepository.findById(id);
        if (matriculaOpt.isPresent()) {
            Matricula matricula = matriculaOpt.get();
            
            // Só pode reativar matrículas canceladas ou trancadas
            if (matricula.isCancelada() || matricula.isTrancada()) {
                matricula.reativar();
                return matriculaRepository.save(matricula);
            } else {
                throw new RuntimeException("Só é possível reativar matrículas canceladas ou trancadas");
            }
        }
        throw new RuntimeException("Matrícula não encontrada");
    }
    
    public void excluir(Long id) {
        if (!matriculaRepository.existsById(id)) {
            throw new RuntimeException("Matrícula não encontrada");
        }
        matriculaRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public long contarPorStatus(Matricula.StatusMatricula status) {
        return matriculaRepository.countByStatus(status);
    }
    
    @Transactional(readOnly = true)
    public long contarAtivas() {
        return matriculaRepository.countByStatus(Matricula.StatusMatricula.ATIVA);
    }
    
    @Transactional(readOnly = true)
    public long contarPorTurma(Turma turma) {
        return matriculaRepository.countByTurma(turma);
    }
    
    @Transactional(readOnly = true)
    public long contarMatriculasAtivasPorTurma(Turma turma) {
        return matriculaRepository.countMatriculasAtivasByTurma(turma);
    }
    
    @Transactional(readOnly = true)
    public boolean alunoMatriculadoNaTurma(Aluno aluno, Turma turma) {
        return matriculaRepository.existsByAlunoAndTurmaAtiva(aluno, turma);
    }
} 