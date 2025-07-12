package br.com.tjw2025.repository;

import br.com.tjw2025.entity.Matricula;
import br.com.tjw2025.entity.Aluno;
import br.com.tjw2025.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    
    // Buscar matrículas por aluno
    List<Matricula> findByAluno(Aluno aluno);
    
    // Buscar matrículas por turma
    List<Matricula> findByTurma(Turma turma);
    
    // Buscar matrícula específica de um aluno em uma turma
    Optional<Matricula> findByAlunoAndTurma(Aluno aluno, Turma turma);
    
    // Buscar matrículas por status
    List<Matricula> findByStatus(Matricula.StatusMatricula status);
    
    // Buscar matrículas por data
    List<Matricula> findByDataMatricula(LocalDate dataMatricula);
    
    // Buscar matrículas por período de data
    @Query("SELECT m FROM Matricula m WHERE m.dataMatricula >= :dataInicio AND m.dataMatricula <= :dataFim")
    List<Matricula> findByPeriodoData(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    // Verificar se aluno já está matriculado na turma
    @Query("SELECT COUNT(m) > 0 FROM Matricula m WHERE m.aluno = :aluno AND m.turma = :turma AND m.status = 'ATIVA'")
    boolean existsByAlunoAndTurmaAtiva(@Param("aluno") Aluno aluno, @Param("turma") Turma turma);
    
    // Contar matrículas por status
    long countByStatus(Matricula.StatusMatricula status);
    
    // Contar matrículas por turma
    long countByTurma(Turma turma);
    
    // Contar matrículas ativas por turma
    @Query("SELECT COUNT(m) FROM Matricula m WHERE m.turma = :turma AND m.status = 'ATIVA'")
    long countMatriculasAtivasByTurma(@Param("turma") Turma turma);
    
    // Buscar matrículas por aluno ordenadas por data de criação
    List<Matricula> findByAlunoOrderByDataCriacaoDesc(Aluno aluno);
    
    // Buscar matrículas por turma ordenadas por data de criação
    List<Matricula> findByTurmaOrderByDataCriacaoDesc(Turma turma);
    
    // Buscar matrículas ativas por aluno
    @Query("SELECT m FROM Matricula m WHERE m.aluno = :aluno AND m.status = 'ATIVA' ORDER BY m.dataCriacao DESC")
    List<Matricula> findMatriculasAtivasByAluno(@Param("aluno") Aluno aluno);
    
    // Buscar matrículas ativas por turma
    @Query("SELECT m FROM Matricula m WHERE m.turma = :turma AND m.status = 'ATIVA' ORDER BY m.dataCriacao DESC")
    List<Matricula> findMatriculasAtivasByTurma(@Param("turma") Turma turma);
    
    // Buscar histórico de matrículas por aluno
    @Query("SELECT m FROM Matricula m WHERE m.aluno = :aluno ORDER BY m.dataCriacao DESC")
    List<Matricula> findHistoricoByAluno(@Param("aluno") Aluno aluno);
} 