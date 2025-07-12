package br.com.tjw2025.repository;

import br.com.tjw2025.entity.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    
    // Buscar por código
    Optional<Disciplina> findByCodigo(String codigo);
    
    // Buscar por nome (contendo)
    List<Disciplina> findByNomeContainingIgnoreCase(String nome);
    
    // Buscar disciplinas ativas
    List<Disciplina> findByAtivaTrue();
    
    // Buscar disciplinas inativas
    List<Disciplina> findByAtivaFalse();
    
    // Buscar por semestre
    List<Disciplina> findBySemestre(String semestre);
    
    // Buscar por tipo
    List<Disciplina> findByTipoContainingIgnoreCase(String tipo);
    
    // Buscar por carga horária
    List<Disciplina> findByCargaHoraria(Integer cargaHoraria);
    
    // Buscar disciplinas com carga horária maior que
    List<Disciplina> findByCargaHorariaGreaterThan(Integer cargaHoraria);
    
    // Buscar disciplinas com carga horária menor que
    List<Disciplina> findByCargaHorariaLessThan(Integer cargaHoraria);
    
    // Verificar se código já existe (excluindo a própria disciplina)
    @Query("SELECT COUNT(d) > 0 FROM Disciplina d WHERE d.codigo = :codigo AND d.id != :id")
    boolean existsByCodigoExcludingId(@Param("codigo") String codigo, @Param("id") Long id);
    
    // Contar disciplinas ativas
    long countByAtivaTrue();
    
    // Contar disciplinas inativas
    long countByAtivaFalse();
    
    // Buscar disciplinas por semestre
    @Query("SELECT DISTINCT d.semestre FROM Disciplina d WHERE d.ativa = true ORDER BY d.semestre")
    List<String> findDistinctSemestre();
    
    // Buscar disciplinas por tipo
    @Query("SELECT DISTINCT d.tipo FROM Disciplina d WHERE d.ativa = true ORDER BY d.tipo")
    List<String> findDistinctTipo();
    
    // Buscar disciplinas ordenadas por nome
    List<Disciplina> findAllByOrderByNomeAsc();
    
    // Buscar disciplinas ativas ordenadas por nome
    List<Disciplina> findByAtivaTrueOrderByNomeAsc();
} 