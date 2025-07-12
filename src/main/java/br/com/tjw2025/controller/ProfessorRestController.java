package br.com.tjw2025.controller;

import br.com.tjw2025.entity.Professor;
import br.com.tjw2025.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professores")
public class ProfessorRestController extends BaseRestController<Professor, Long> {
    
    private final ProfessorService professorService;
    
    @Autowired
    public ProfessorRestController(ProfessorService professorService) {
        super(professorService);
        this.professorService = professorService;
    }

    @PostMapping
    public Professor criar(@RequestBody Professor professor) {
        return service.salvar(professor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> atualizar(@PathVariable Long id, @RequestBody Professor professorAtualizado) {
        try {
            // Buscar professor existente
            Professor professorExistente = service.buscarPorId(id).orElse(null);
            if (professorExistente == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Fazer merge dos dados (manter dados existentes se não fornecidos)
            if (professorAtualizado.getNome() != null) {
                professorExistente.setNome(professorAtualizado.getNome());
            }
            if (professorAtualizado.getEmail() != null) {
                professorExistente.setEmail(professorAtualizado.getEmail());
            }
            if (professorAtualizado.getCpf() != null) {
                professorExistente.setCpf(professorAtualizado.getCpf());
            }
            if (professorAtualizado.getDataNascimento() != null) {
                professorExistente.setDataNascimento(professorAtualizado.getDataNascimento());
            }
            if (professorAtualizado.getTelefone() != null) {
                professorExistente.setTelefone(professorAtualizado.getTelefone());
            }
            if (professorAtualizado.getAreaAtuacao() != null) {
                professorExistente.setAreaAtuacao(professorAtualizado.getAreaAtuacao());
            }
            if (professorAtualizado.getTitulacao() != null) {
                professorExistente.setTitulacao(professorAtualizado.getTitulacao());
            }
            if (professorAtualizado.getEndereco() != null) {
                professorExistente.setEndereco(professorAtualizado.getEndereco());
            }
            if (professorAtualizado.getCidade() != null) {
                professorExistente.setCidade(professorAtualizado.getCidade());
            }
            if (professorAtualizado.getEstado() != null) {
                professorExistente.setEstado(professorAtualizado.getEstado());
            }
            if (professorAtualizado.getCep() != null) {
                professorExistente.setCep(professorAtualizado.getCep());
            }
            if (professorAtualizado.getDataContratacao() != null) {
                professorExistente.setDataContratacao(professorAtualizado.getDataContratacao());
            }
            
            // Salvar e retornar
            Professor professorSalvo = service.salvar(professorExistente);
            return ResponseEntity.ok(professorSalvo);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!service.buscarPorId(id).isPresent()) return ResponseEntity.notFound().build();
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
} 