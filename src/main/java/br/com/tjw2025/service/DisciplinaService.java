package br.com.tjw2025.service;

import br.com.tjw2025.entity.Disciplina;
import br.com.tjw2025.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DisciplinaService {
    
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    
    public Disciplina salvar(Disciplina disciplina) {
        if (disciplina.getId() != null) {
            if (disciplinaRepository.existsByCodigoExcludingId(disciplina.getCodigo(), disciplina.getId())) {
                throw new RuntimeException("Código já cadastrado para outra disciplina");
            }
        }
        return disciplinaRepository.save(disciplina);
    }
    
    @Transactional(readOnly = true)
    public Optional<Disciplina> buscarPorId(Long id) {
        return disciplinaRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public Optional<Disciplina> buscarPorCodigo(String codigo) {
        return disciplinaRepository.findByCodigo(codigo);
    }
    
    @Transactional(readOnly = true)
    public List<Disciplina> listarTodos() {
        return disciplinaRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Disciplina> listarAtivas() {
        return disciplinaRepository.findByAtivaTrue();
    }
    
    @Transactional(readOnly = true)
    public List<Disciplina> listarInativas() {
        return disciplinaRepository.findByAtivaFalse();
    }
    
    @Transactional(readOnly = true)
    public List<Disciplina> buscarPorNome(String nome) {
        return disciplinaRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    @Transactional(readOnly = true)
    public List<Disciplina> buscarPorSemestre(String semestre) {
        return disciplinaRepository.findBySemestre(semestre);
    }
    
    @Transactional(readOnly = true)
    public List<Disciplina> buscarPorTipo(String tipo) {
        return disciplinaRepository.findByTipoContainingIgnoreCase(tipo);
    }
    
    public Disciplina ativar(Long id) {
        Optional<Disciplina> disciplinaOpt = disciplinaRepository.findById(id);
        if (disciplinaOpt.isPresent()) {
            Disciplina disciplina = disciplinaOpt.get();
            disciplina.setAtiva(true);
            return disciplinaRepository.save(disciplina);
        }
        throw new RuntimeException("Disciplina não encontrada");
    }
    
    public Disciplina inativar(Long id) {
        Optional<Disciplina> disciplinaOpt = disciplinaRepository.findById(id);
        if (disciplinaOpt.isPresent()) {
            Disciplina disciplina = disciplinaOpt.get();
            disciplina.setAtiva(false);
            return disciplinaRepository.save(disciplina);
        }
        throw new RuntimeException("Disciplina não encontrada");
    }
    
    public void excluir(Long id) {
        Optional<Disciplina> disciplinaOpt = disciplinaRepository.findById(id);
        if (disciplinaOpt.isPresent()) {
            Disciplina disciplina = disciplinaOpt.get();
            disciplina.setAtiva(false);
            disciplinaRepository.save(disciplina);
        } else {
            throw new RuntimeException("Disciplina não encontrada");
        }
    }
    
    @Transactional(readOnly = true)
    public long contarAtivas() {
        return disciplinaRepository.countByAtivaTrue();
    }
    
    @Transactional(readOnly = true)
    public long contarInativas() {
        return disciplinaRepository.countByAtivaFalse();
    }
    
    @Transactional(readOnly = true)
    public List<String> listarSemestres() {
        return disciplinaRepository.findDistinctSemestre();
    }
    
    @Transactional(readOnly = true)
    public List<String> listarTipos() {
        return disciplinaRepository.findDistinctTipo();
    }
} 