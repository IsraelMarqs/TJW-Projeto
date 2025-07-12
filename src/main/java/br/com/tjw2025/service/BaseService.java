package br.com.tjw2025.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T, ID> {
    
    protected final JpaRepository<T, ID> repository;
    
    public BaseService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }
    
    // Salvar ou atualizar entidade
    @Transactional
    public T salvar(T entity) {
        return repository.save(entity);
    }
    
    // Buscar por ID
    @Transactional(readOnly = true)
    public Optional<T> buscarPorId(ID id) {
        return repository.findById(id);
    }
    
    // Listar todos
    @Transactional(readOnly = true)
    public List<T> listarTodos() {
        return repository.findAll();
    }
    
    // Excluir por ID
    @Transactional
    public void excluir(ID id) {
        repository.deleteById(id);
    }
    
    // Verificar se existe por ID
    @Transactional(readOnly = true)
    public boolean existePorId(ID id) {
        return repository.existsById(id);
    }
    
    // Contar total
    @Transactional(readOnly = true)
    public long contar() {
        return repository.count();
    }
} 