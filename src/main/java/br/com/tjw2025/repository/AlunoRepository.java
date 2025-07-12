package br.com.tjw2025.repository;

import br.com.tjw2025.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    
    // Buscar por nome (contendo)
    List<Aluno> findByNomeContainingIgnoreCase(String nome);
    
    // Buscar por email
    Optional<Aluno> findByEmail(String email);
    
    // Buscar por CPF
    Optional<Aluno> findByCpf(String cpf);
    
    // Buscar alunos ativos
    List<Aluno> findByAtivoTrue();
    
    // Buscar alunos inativos
    List<Aluno> findByAtivoFalse();
    
    // Buscar por cidade
    List<Aluno> findByCidadeIgnoreCase(String cidade);
    
    // Buscar por estado
    List<Aluno> findByEstadoIgnoreCase(String estado);
    
    // Buscar por ano de matrícula
    @Query("SELECT a FROM Aluno a WHERE YEAR(a.dataMatricula) = :ano")
    List<Aluno> findByAnoMatricula(@Param("ano") int ano);
    
    // Verificar se email já existe (excluindo o próprio aluno)
    @Query("SELECT COUNT(a) > 0 FROM Aluno a WHERE a.email = :email AND a.id != :id")
    boolean existsByEmailExcludingId(@Param("email") String email, @Param("id") Long id);
    
    // Verificar se CPF já existe (excluindo o próprio aluno)
    @Query("SELECT COUNT(a) > 0 FROM Aluno a WHERE a.cpf = :cpf AND a.id != :id")
    boolean existsByCpfExcludingId(@Param("cpf") String cpf, @Param("id") Long id);
    
    // Contar alunos ativos
    long countByAtivoTrue();
    
    // Contar alunos inativos
    long countByAtivoFalse();
} 