package br.com.tjw2025.repository;

import br.com.tjw2025.entity.Turma;
import br.com.tjw2025.entity.Disciplina;
import br.com.tjw2025.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {
    
    // Buscar por código
    Optional<Turma> findByCodigo(String codigo);
    
    // Buscar turmas ativas
    List<Turma> findByAtivaTrue();
    
    // Buscar turmas inativas
    List<Turma> findByAtivaFalse();
    
    // Buscar por disciplina
    List<Turma> findByDisciplina(Disciplina disciplina);
    
    // Buscar por professor
    List<Turma> findByProfessor(Professor professor);
    
    // Buscar por dia da semana
    List<Turma> findByDiaSemanaIgnoreCase(String diaSemana);
    
    // Buscar por sala
    List<Turma> findBySalaContainingIgnoreCase(String sala);
    
    // Buscar turmas com vagas disponíveis
    @Query("SELECT t FROM Turma t WHERE t.ativa = true AND t.vagasOcupadas < t.vagas")
    List<Turma> findTurmasComVagasDisponiveis();
    
    // Buscar turmas lotadas
    @Query("SELECT t FROM Turma t WHERE t.ativa = true AND t.vagasOcupadas >= t.vagas")
    List<Turma> findTurmasLotadas();
    
    // Verificar se código já existe (excluindo a própria turma)
    @Query("SELECT COUNT(t) > 0 FROM Turma t WHERE t.codigo = :codigo AND t.id != :id")
    boolean existsByCodigoExcludingId(@Param("codigo") String codigo, @Param("id") Long id);
    
    // Contar turmas ativas
    long countByAtivaTrue();
    
    // Contar turmas inativas
    long countByAtivaFalse();
    
    // Buscar turmas por período de data
    @Query("SELECT t FROM Turma t WHERE t.ativa = true AND t.dataInicio >= :dataInicio AND t.dataFim <= :dataFim")
    List<Turma> findByPeriodoData(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    // Buscar turmas que começam em uma data específica
    List<Turma> findByDataInicio(LocalDate dataInicio);
    
    // Buscar turmas que terminam em uma data específica
    List<Turma> findByDataFim(LocalDate dataFim);
    
    // Buscar turmas ordenadas por código
    List<Turma> findAllByOrderByCodigoAsc();
    
    // Buscar turmas ativas ordenadas por código
    List<Turma> findByAtivaTrueOrderByCodigoAsc();
    
    // Buscar turmas por disciplina ordenadas por código
    List<Turma> findByDisciplinaOrderByCodigoAsc(Disciplina disciplina);
} 