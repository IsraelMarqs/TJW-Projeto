package br.com.tjw2025.controller;

import br.com.tjw2025.dto.MatriculaDTO;
import br.com.tjw2025.entity.Aluno;
import br.com.tjw2025.entity.Matricula;
import br.com.tjw2025.entity.Turma;
import br.com.tjw2025.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaRestController {
    @Autowired
    private MatriculaService matriculaService;

    @GetMapping
    public List<MatriculaDTO> listarTodos() {
        return matriculaService.listarAtivas().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatriculaDTO> buscarPorId(@PathVariable Long id) {
        Matricula matricula = matriculaService.buscarPorId(id).orElse(null);
        if (matricula == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(toDTO(matricula));
    }

    @PostMapping
    public MatriculaDTO criar(@RequestBody MatriculaDTO dto) {
        Matricula matricula = toEntity(dto);
        Matricula saved = matriculaService.salvar(matricula);
        return toDTO(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatriculaDTO> atualizar(@PathVariable Long id, @RequestBody MatriculaDTO dto) {
        if (!matriculaService.buscarPorId(id).isPresent()) return ResponseEntity.notFound().build();
        Matricula matricula = toEntity(dto);
        matricula.setId(id);
        Matricula saved = matriculaService.salvar(matricula);
        return ResponseEntity.ok(toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!matriculaService.buscarPorId(id).isPresent()) return ResponseEntity.notFound().build();
        matriculaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private Matricula toEntity(MatriculaDTO dto) {
        Matricula matricula = new Matricula();
        if (dto.getAlunoId() != null) {
            Aluno aluno = new Aluno();
            aluno.setId(dto.getAlunoId());
            matricula.setAluno(aluno);
        } else {
            throw new IllegalArgumentException("alunoId é obrigatório");
        }
        if (dto.getTurmaId() != null) {
            Turma turma = new Turma();
            turma.setId(dto.getTurmaId());
            matricula.setTurma(turma);
        } else {
            throw new IllegalArgumentException("turmaId é obrigatório");
        }
        if (dto.getDataMatricula() != null) {
            matricula.setDataMatricula(LocalDate.parse(dto.getDataMatricula()));
        }
        matricula.setStatus(dto.getStatus() != null ? Matricula.StatusMatricula.valueOf(dto.getStatus()) : Matricula.StatusMatricula.ATIVA);
        matricula.setObservacoes(dto.getObservacoes());
        return matricula;
    }

    private MatriculaDTO toDTO(Matricula matricula) {
        MatriculaDTO dto = new MatriculaDTO();
        dto.setId(matricula.getId());
        dto.setTurmaId(matricula.getTurma() != null ? matricula.getTurma().getId() : null);
        dto.setAlunoId(matricula.getAluno() != null ? matricula.getAluno().getId() : null);
        dto.setStatus(matricula.getStatus() != null ? matricula.getStatus().name() : null);
        dto.setDataMatricula(matricula.getDataMatricula() != null ? matricula.getDataMatricula().toString() : null);
        dto.setObservacoes(matricula.getObservacoes());
        return dto;
    }
} 