package br.com.tjw2025.controller;

import br.com.tjw2025.entity.Semestre;
import br.com.tjw2025.service.SemestreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/semestres")
public class SemestreRestController {
    @Autowired
    private SemestreService semestreService;

    @GetMapping
    public List<Semestre> listarTodos() {
        return semestreService.listarAtivos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Semestre> buscarPorId(@PathVariable Long id) {
        Semestre semestre = semestreService.buscarPorId(id).orElse(null);
        if (semestre == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(semestre);
    }

    @PostMapping
    public Semestre criar(@RequestBody Semestre semestre) {
        return semestreService.salvar(semestre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Semestre> atualizar(@PathVariable Long id, @RequestBody Semestre semestreAtualizado) {
        try {
            // Buscar semestre existente
            Semestre semestreExistente = semestreService.buscarPorId(id).orElse(null);
            if (semestreExistente == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Fazer merge dos dados (manter dados existentes se não fornecidos)
            if (semestreAtualizado.getAno() != null) {
                semestreExistente.setAno(semestreAtualizado.getAno());
            }
            if (semestreAtualizado.getPeriodo() != null) {
                semestreExistente.setPeriodo(semestreAtualizado.getPeriodo());
            }
            if (semestreAtualizado.getDataInicio() != null) {
                semestreExistente.setDataInicio(semestreAtualizado.getDataInicio());
            }
            if (semestreAtualizado.getDataFim() != null) {
                semestreExistente.setDataFim(semestreAtualizado.getDataFim());
            }
            if (semestreAtualizado.getDataInicioMatriculas() != null) {
                semestreExistente.setDataInicioMatriculas(semestreAtualizado.getDataInicioMatriculas());
            }
            if (semestreAtualizado.getDataFimMatriculas() != null) {
                semestreExistente.setDataFimMatriculas(semestreAtualizado.getDataFimMatriculas());
            }
            
            // Salvar e retornar
            Semestre semestreSalvo = semestreService.salvar(semestreExistente);
            return ResponseEntity.ok(semestreSalvo);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!semestreService.buscarPorId(id).isPresent()) return ResponseEntity.notFound().build();
        semestreService.excluir(id);
        return ResponseEntity.noContent().build();
    }
} 