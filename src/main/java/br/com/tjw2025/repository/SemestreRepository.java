package br.com.tjw2025.repository;

import br.com.tjw2025.entity.Semestre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SemestreRepository extends JpaRepository<Semestre, Long> {
    
    // Buscar por ano
    List<Semestre> findByAno(String ano);
    
    // Buscar por período
    List<Semestre> findByPeriodo(String periodo);
    
    // Buscar por ano e período
    Optional<Semestre> findByAnoAndPeriodo(String ano, String periodo);
    
    // Buscar semestres ativos
    List<Semestre> findByAtivoTrue();
    
    // Buscar semestres inativos
    List<Semestre> findByAtivoFalse();
    
    // Buscar semestre atual (baseado na data atual)
    @Query("SELECT s FROM Semestre s WHERE s.ativo = true AND s.dataInicio <= :dataAtual AND s.dataFim >= :dataAtual")
    Optional<Semestre> findSemestreAtual(@Param("dataAtual") LocalDate dataAtual);
    
    // Buscar semestres com matrículas abertas
    @Query("SELECT s FROM Semestre s WHERE s.ativo = true AND s.dataInicioMatriculas <= :dataAtual AND s.dataFimMatriculas >= :dataAtual")
    List<Semestre> findSemestresComMatriculasAbertas(@Param("dataAtual") LocalDate dataAtual);
    
    // Buscar semestres futuros
    @Query("SELECT s FROM Semestre s WHERE s.ativo = true AND s.dataInicio > :dataAtual ORDER BY s.dataInicio")
    List<Semestre> findSemestresFuturos(@Param("dataAtual") LocalDate dataAtual);
    
    // Buscar semestres passados
    @Query("SELECT s FROM Semestre s WHERE s.ativo = true AND s.dataFim < :dataAtual ORDER BY s.dataFim DESC")
    List<Semestre> findSemestresPassados(@Param("dataAtual") LocalDate dataAtual);
    
    // Contar semestres ativos
    long countByAtivoTrue();
    
    // Contar semestres inativos
    long countByAtivoFalse();
    
    // Buscar anos distintos
    @Query("SELECT DISTINCT s.ano FROM Semestre s WHERE s.ativo = true ORDER BY s.ano DESC")
    List<String> findDistinctAno();
    
    // Buscar semestres ordenados por ano e período
    List<Semestre> findAllByOrderByAnoDescPeriodoDesc();
    
    // Buscar semestres ativos ordenados por ano e período
    List<Semestre> findByAtivoTrueOrderByAnoDescPeriodoDesc();
} 