package br.com.tjw2025.service;

import br.com.tjw2025.entity.Semestre;
import br.com.tjw2025.repository.SemestreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SemestreService {

    @Autowired
    private SemestreRepository semestreRepository;

    public List<Semestre> listarAtivos() {
        return semestreRepository.findByAtivoTrue();
    }

    public List<Semestre> listarTodos() {
        return semestreRepository.findAll();
    }

    public Optional<Semestre> buscarPorId(Long id) {
        return semestreRepository.findById(id);
    }

    @Transactional
    public Semestre salvar(Semestre semestre) {
        return semestreRepository.save(semestre);
    }

    @Transactional
    public void excluir(Long id) {
        semestreRepository.deleteById(id);
    }

    public long contarAtivos() {
        return semestreRepository.countByAtivoTrue();
    }
} 