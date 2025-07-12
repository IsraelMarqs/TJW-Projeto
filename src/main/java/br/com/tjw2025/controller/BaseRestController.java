package br.com.tjw2025.controller;

import br.com.tjw2025.service.BaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public abstract class BaseRestController<T, ID> {
    
    protected final BaseService<T, ID> service;
    
    public BaseRestController(BaseService<T, ID> service) {
        this.service = service;
    }
    
    @GetMapping
    public List<T> listarTodos() {
        return service.listarTodos();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<T> buscarPorId(@PathVariable ID id) {
        Optional<T> entity = service.buscarPorId(id);
        if (entity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entity.get());
    }
    
    @PostMapping
    public T criar(@RequestBody T entity) {
        return service.salvar(entity);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable ID id) {
        if (!service.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
} 