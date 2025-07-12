package br.com.tjw2025.repository;

import br.com.tjw2025.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    
    // Buscar por nome (contendo)
    List<Professor> findByNomeContainingIgnoreCase(String nome);
    
    // Buscar por email
    Optional<Professor> findByEmail(String email);
    
    // Buscar por CPF
    Optional<Professor> findByCpf(String cpf);
    
    // Buscar professores ativos
    List<Professor> findByAtivoTrue();
    
    // Buscar professores inativos
    List<Professor> findByAtivoFalse();
    
    // Buscar por cidade
    List<Professor> findByCidadeIgnoreCase(String cidade);
    
    // Buscar por estado
    List<Professor> findByEstadoIgnoreCase(String estado);
    
    // Buscar por área de atuação
    List<Professor> findByAreaAtuacaoContainingIgnoreCase(String areaAtuacao);
    
    // Buscar por titulação
    List<Professor> findByTitulacaoContainingIgnoreCase(String titulacao);
    
    // Verificar se email já existe (excluindo o próprio professor)
    @Query("SELECT COUNT(p) > 0 FROM Professor p WHERE p.email = :email AND p.id != :id")
    boolean existsByEmailExcludingId(@Param("email") String email, @Param("id") Long id);
    
    // Verificar se CPF já existe (excluindo o próprio professor)
    @Query("SELECT COUNT(p) > 0 FROM Professor p WHERE p.cpf = :cpf AND p.id != :id")
    boolean existsByCpfExcludingId(@Param("cpf") String cpf, @Param("id") Long id);
    
    // Contar professores ativos
    long countByAtivoTrue();
    
    // Contar professores inativos
    long countByAtivoFalse();
    
    // Buscar professores por área de atuação
    @Query("SELECT DISTINCT p.areaAtuacao FROM Professor p WHERE p.ativo = true ORDER BY p.areaAtuacao")
    List<String> findDistinctAreaAtuacao();
    
    // Buscar professores por titulação
    @Query("SELECT DISTINCT p.titulacao FROM Professor p WHERE p.ativo = true ORDER BY p.titulacao")
    List<String> findDistinctTitulacao();
} 