package br.com.tjw2025.controller;

import br.com.tjw2025.entity.Turma;
import br.com.tjw2025.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turmas")
public class TurmaRestController {
    @Autowired
    private TurmaService turmaService;

    @GetMapping
    public List<Turma> listarTodos() {
        return turmaService.listarAtivas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turma> buscarPorId(@PathVariable Long id) {
        Turma turma = turmaService.buscarPorId(id).orElse(null);
        if (turma == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(turma);
    }

    @PostMapping
    public Turma criar(@RequestBody Turma turma) {
        return turmaService.salvar(turma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turma> atualizar(@PathVariable Long id, @RequestBody Turma turmaAtualizada) {
        try {
            // Buscar turma existente
            Turma turmaExistente = turmaService.buscarPorId(id).orElse(null);
            if (turmaExistente == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Fazer merge dos dados (manter dados existentes se não fornecidos)
            if (turmaAtualizada.getCodigo() != null) {
                turmaExistente.setCodigo(turmaAtualizada.getCodigo());
            }
            if (turmaAtualizada.getDisciplina() != null) {
                turmaExistente.setDisciplina(turmaAtualizada.getDisciplina());
            }
            if (turmaAtualizada.getProfessor() != null) {
                turmaExistente.setProfessor(turmaAtualizada.getProfessor());
            }
            if (turmaAtualizada.getVagas() != null) {
                turmaExistente.setVagas(turmaAtualizada.getVagas());
            }
            if (turmaAtualizada.getDiaSemana() != null) {
                turmaExistente.setDiaSemana(turmaAtualizada.getDiaSemana());
            }
            if (turmaAtualizada.getHorarioInicio() != null) {
                turmaExistente.setHorarioInicio(turmaAtualizada.getHorarioInicio());
            }
            if (turmaAtualizada.getHorarioFim() != null) {
                turmaExistente.setHorarioFim(turmaAtualizada.getHorarioFim());
            }
            if (turmaAtualizada.getSala() != null) {
                turmaExistente.setSala(turmaAtualizada.getSala());
            }
            if (turmaAtualizada.getDataInicio() != null) {
                turmaExistente.setDataInicio(turmaAtualizada.getDataInicio());
            }
            if (turmaAtualizada.getDataFim() != null) {
                turmaExistente.setDataFim(turmaAtualizada.getDataFim());
            }
            
            // Salvar e retornar
            Turma turmaSalva = turmaService.salvar(turmaExistente);
            return ResponseEntity.ok(turmaSalva);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!turmaService.buscarPorId(id).isPresent()) return ResponseEntity.notFound().build();
        turmaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
} 