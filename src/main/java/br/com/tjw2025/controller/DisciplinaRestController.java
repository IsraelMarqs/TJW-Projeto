package br.com.tjw2025.controller;

import br.com.tjw2025.entity.Disciplina;
import br.com.tjw2025.service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disciplinas")
public class DisciplinaRestController {
    @Autowired
    private DisciplinaService disciplinaService;

    @GetMapping
    public List<Disciplina> listarTodos() {
        return disciplinaService.listarAtivas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> buscarPorId(@PathVariable Long id) {
        Disciplina disciplina = disciplinaService.buscarPorId(id).orElse(null);
        if (disciplina == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(disciplina);
    }

    @PostMapping
    public Disciplina criar(@RequestBody Disciplina disciplina) {
        return disciplinaService.salvar(disciplina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Disciplina> atualizar(@PathVariable Long id, @RequestBody Disciplina disciplinaAtualizada) {
        try {
            // Buscar disciplina existente
            Disciplina disciplinaExistente = disciplinaService.buscarPorId(id).orElse(null);
            if (disciplinaExistente == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Fazer merge dos dados (manter dados existentes se não fornecidos)
            if (disciplinaAtualizada.getCodigo() != null) {
                disciplinaExistente.setCodigo(disciplinaAtualizada.getCodigo());
            }
            if (disciplinaAtualizada.getNome() != null) {
                disciplinaExistente.setNome(disciplinaAtualizada.getNome());
            }
            if (disciplinaAtualizada.getEmenta() != null) {
                disciplinaExistente.setEmenta(disciplinaAtualizada.getEmenta());
            }
            if (disciplinaAtualizada.getCargaHoraria() != null) {
                disciplinaExistente.setCargaHoraria(disciplinaAtualizada.getCargaHoraria());
            }
            if (disciplinaAtualizada.getPreRequisitos() != null) {
                disciplinaExistente.setPreRequisitos(disciplinaAtualizada.getPreRequisitos());
            }
            if (disciplinaAtualizada.getCreditos() != null) {
                disciplinaExistente.setCreditos(disciplinaAtualizada.getCreditos());
            }
            if (disciplinaAtualizada.getSemestre() != null) {
                disciplinaExistente.setSemestre(disciplinaAtualizada.getSemestre());
            }
            if (disciplinaAtualizada.getTipo() != null) {
                disciplinaExistente.setTipo(disciplinaAtualizada.getTipo());
            }
            
            // Salvar e retornar
            Disciplina disciplinaSalva = disciplinaService.salvar(disciplinaExistente);
            return ResponseEntity.ok(disciplinaSalva);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!disciplinaService.buscarPorId(id).isPresent()) return ResponseEntity.notFound().build();
        disciplinaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
} 