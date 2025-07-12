package br.com.tjw2025.service;

import br.com.tjw2025.entity.Aluno;
import br.com.tjw2025.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AlunoService extends BaseService<Aluno, Long> {
    
    private final AlunoRepository alunoRepository;
    
    @Autowired
    public AlunoService(AlunoRepository alunoRepository) {
        super(alunoRepository);
        this.alunoRepository = alunoRepository;
    }
    
    // Salvar ou atualizar aluno
    @Override
    public Aluno salvar(Aluno aluno) {
        // Validações de negócio
        if (aluno.getId() == null) {
            // Novo aluno - definir data de matrícula se não informada
            if (aluno.getDataMatricula() == null) {
                aluno.setDataMatricula(LocalDate.now());
            }
        } else {
            // Aluno existente - verificar se email/CPF já existem para outros alunos
            if (alunoRepository.existsByEmailExcludingId(aluno.getEmail(), aluno.getId())) {
                throw new RuntimeException("Email já cadastrado para outro aluno");
            }
            if (alunoRepository.existsByCpfExcludingId(aluno.getCpf(), aluno.getId())) {
                throw new RuntimeException("CPF já cadastrado para outro aluno");
            }
        }
        
        return super.salvar(aluno);
    }
    
    // Listar alunos ativos
    @Transactional(readOnly = true)
    public List<Aluno> listarAtivos() {
        return alunoRepository.findByAtivoTrue();
    }
    
    // Buscar por ano de matrícula
    @Transactional(readOnly = true)
    public List<Aluno> buscarPorAnoMatricula(int ano) {
        return alunoRepository.findByAnoMatricula(ano);
    }
    
    // Excluir aluno permanentemente
    public void excluirPermanentemente(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Aluno não encontrado");
        }
        repository.deleteById(id);
    }
    
    public void removerPorId(Long id) {
        Optional<Aluno> alunoOpt = repository.findById(id);
        if (alunoOpt.isPresent()) {
            Aluno aluno = alunoOpt.get();
            // Garante que as matrículas estão carregadas
            if (aluno.getMatriculas() != null) {
                aluno.getMatriculas().size();
            }
            repository.delete(aluno);
        }
    }
} 