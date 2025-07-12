package br.com.tjw2025.controller;

import br.com.tjw2025.entity.Aluno;
import br.com.tjw2025.service.AlunoService;
import br.com.tjw2025.dto.AlunoDTO;
import br.com.tjw2025.dto.MatriculaDTO;
import br.com.tjw2025.entity.Matricula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/alunos")
public class AlunoRestController extends BaseRestController<Aluno, Long> {
    
    private final AlunoService alunoService;
    
    @Autowired
    public AlunoRestController(AlunoService alunoService) {
        super(alunoService);
        this.alunoService = alunoService;
    }

    @Override
    @GetMapping
    public List<Aluno> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/dto")
    public List<AlunoDTO> listarTodosDTO() {
        return alunoService.listarAtivos().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        Aluno aluno = service.buscarPorId(id).orElse(null);
        if (aluno == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(aluno);
    }

    @GetMapping("/{id}/dto")
    public ResponseEntity<AlunoDTO> buscarPorIdDTO(@PathVariable Long id) {
        Aluno aluno = service.buscarPorId(id).orElse(null);
        if (aluno == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(toDTO(aluno));
    }

    @PostMapping
    public Aluno criar(@RequestBody Aluno aluno) {
        return service.salvar(aluno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @RequestBody Aluno alunoAtualizado) {
        try {
            // Buscar aluno existente
            Aluno alunoExistente = service.buscarPorId(id).orElse(null);
            if (alunoExistente == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Fazer merge dos dados (manter dados existentes se não fornecidos)
            if (alunoAtualizado.getNome() != null) {
                alunoExistente.setNome(alunoAtualizado.getNome());
            }
            if (alunoAtualizado.getEmail() != null) {
                alunoExistente.setEmail(alunoAtualizado.getEmail());
            }
            if (alunoAtualizado.getCpf() != null) {
                alunoExistente.setCpf(alunoAtualizado.getCpf());
            }
            if (alunoAtualizado.getDataNascimento() != null) {
                alunoExistente.setDataNascimento(alunoAtualizado.getDataNascimento());
            }
            if (alunoAtualizado.getTelefone() != null) {
                alunoExistente.setTelefone(alunoAtualizado.getTelefone());
            }
            if (alunoAtualizado.getEndereco() != null) {
                alunoExistente.setEndereco(alunoAtualizado.getEndereco());
            }
            if (alunoAtualizado.getCidade() != null) {
                alunoExistente.setCidade(alunoAtualizado.getCidade());
            }
            if (alunoAtualizado.getEstado() != null) {
                alunoExistente.setEstado(alunoAtualizado.getEstado());
            }
            if (alunoAtualizado.getCep() != null) {
                alunoExistente.setCep(alunoAtualizado.getCep());
            }
            
            // Salvar e retornar
            Aluno alunoSalvo = service.salvar(alunoExistente);
            return ResponseEntity.ok(alunoSalvo);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!service.buscarPorId(id).isPresent()) return ResponseEntity.notFound().build();
        ((AlunoService) service).removerPorId(id);
        return ResponseEntity.noContent().build();
    }

    private AlunoDTO toDTO(Aluno aluno) {
        AlunoDTO dto = new AlunoDTO();
        dto.setId(aluno.getId());
        dto.setNome(aluno.getNome());
        dto.setEmail(aluno.getEmail());
        dto.setCpf(aluno.getCpf());
        dto.setDataNascimento(aluno.getDataNascimento() != null ? aluno.getDataNascimento().toString() : null);
        dto.setTelefone(aluno.getTelefone());
        dto.setEndereco(aluno.getEndereco());
        dto.setCidade(aluno.getCidade());
        dto.setEstado(aluno.getEstado());
        dto.setCep(aluno.getCep());
        dto.setDataMatricula(aluno.getDataMatricula() != null ? aluno.getDataMatricula().toString() : null);
        dto.setAtivo(aluno.isAtivo());
        if (aluno.getMatriculas() != null) {
            dto.setMatriculas(aluno.getMatriculas().stream().map(this::toMatriculaDTO).collect(Collectors.toList()));
        }
        return dto;
    }

    private MatriculaDTO toMatriculaDTO(Matricula matricula) {
        MatriculaDTO dto = new MatriculaDTO();
        dto.setId(matricula.getId());
        dto.setTurmaId(matricula.getTurma() != null ? matricula.getTurma().getId() : null);
        dto.setStatus(matricula.getStatus() != null ? matricula.getStatus().name() : null);
        dto.setDataMatricula(matricula.getDataMatricula() != null ? matricula.getDataMatricula().toString() : null);
        dto.setObservacoes(matricula.getObservacoes());
        return dto;
    }
} 