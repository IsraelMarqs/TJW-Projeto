package br.com.tjw2025.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "alunos")
public class Aluno extends BaseEntity {
    
    @NotNull(message = "Data de matrícula é obrigatória")
    private LocalDate dataMatricula;
    
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Matricula> matriculas = new java.util.ArrayList<>();
    
    // Construtores
    public Aluno() {}
    
    public Aluno(String nome, String email, String cpf, LocalDate dataNascimento, 
                 String telefone, String endereco, String cidade, String estado, 
                 String cep, LocalDate dataMatricula) {
        super(nome, email, cpf, dataNascimento, telefone, endereco, cidade, estado, cep);
        this.dataMatricula = dataMatricula;
    }
    
    // Getters e Setters específicos do Aluno
    public LocalDate getDataMatricula() {
        return dataMatricula;
    }
    
    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }
    
    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = (matriculas != null) ? matriculas : new java.util.ArrayList<>();
    }
    
    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", dataNascimento=" + getDataNascimento() +
                ", telefone='" + getTelefone() + '\'' +
                ", endereco='" + getEndereco() + '\'' +
                ", cidade='" + getCidade() + '\'' +
                ", estado='" + getEstado() + '\'' +
                ", cep='" + getCep() + '\'' +
                ", dataMatricula=" + dataMatricula +
                ", ativo=" + isAtivo() +
                '}';
    }
} 