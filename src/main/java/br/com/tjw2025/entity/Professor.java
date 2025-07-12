package br.com.tjw2025.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "professores")
public class Professor extends BaseEntity {
    
    @NotBlank(message = "Área de atuação é obrigatória")
    private String areaAtuacao;
    
    @NotBlank(message = "Titulação é obrigatória")
    private String titulacao;
    
    @NotNull(message = "Data de contratação é obrigatória")
    private LocalDate dataContratacao;
    
    // Construtores
    public Professor() {}
    
    public Professor(String nome, String email, String cpf, LocalDate dataNascimento, 
                    String telefone, String areaAtuacao, String titulacao, 
                    String endereco, String cidade, String estado, String cep, 
                    LocalDate dataContratacao) {
        super(nome, email, cpf, dataNascimento, telefone, endereco, cidade, estado, cep);
        this.areaAtuacao = areaAtuacao;
        this.titulacao = titulacao;
        this.dataContratacao = dataContratacao;
    }
    
    // Getters e Setters específicos do Professor
    public String getAreaAtuacao() {
        return areaAtuacao;
    }
    
    public void setAreaAtuacao(String areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }
    
    public String getTitulacao() {
        return titulacao;
    }
    
    public void setTitulacao(String titulacao) {
        this.titulacao = titulacao;
    }
    
    public LocalDate getDataContratacao() {
        return dataContratacao;
    }
    
    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }
    
    @Override
    public String toString() {
        return "Professor{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", dataNascimento=" + getDataNascimento() +
                ", telefone='" + getTelefone() + '\'' +
                ", areaAtuacao='" + areaAtuacao + '\'' +
                ", titulacao='" + titulacao + '\'' +
                ", endereco='" + getEndereco() + '\'' +
                ", cidade='" + getCidade() + '\'' +
                ", estado='" + getEstado() + '\'' +
                ", cep='" + getCep() + '\'' +
                ", dataContratacao=" + dataContratacao +
                ", ativo=" + isAtivo() +
                '}';
    }
} 