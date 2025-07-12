package br.com.tjw2025.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "disciplinas")
public class Disciplina {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Código é obrigatório")
    @Column(unique = true)
    private String codigo;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;
    
    @NotBlank(message = "Ementa é obrigatória")
    @Column(columnDefinition = "TEXT")
    private String ementa;
    
    @NotNull(message = "Carga horária é obrigatória")
    @Min(value = 1, message = "Carga horária deve ser maior que 0")
    private Integer cargaHoraria;
    
    @NotBlank(message = "Pré-requisitos são obrigatórios")
    private String preRequisitos;
    
    @NotBlank(message = "Créditos são obrigatórios")
    private String creditos;
    
    @NotBlank(message = "Semestre é obrigatório")
    private String semestre;
    
    @NotBlank(message = "Tipo é obrigatório")
    private String tipo; // Obrigatória, Eletiva, etc.
    
    private boolean ativa = true;
    
    // Construtores
    public Disciplina() {}
    
    public Disciplina(String codigo, String nome, String ementa, Integer cargaHoraria, 
                     String preRequisitos, String creditos, String semestre, String tipo) {
        this.codigo = codigo;
        this.nome = nome;
        this.ementa = ementa;
        this.cargaHoraria = cargaHoraria;
        this.preRequisitos = preRequisitos;
        this.creditos = creditos;
        this.semestre = semestre;
        this.tipo = tipo;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEmenta() {
        return ementa;
    }
    
    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }
    
    public Integer getCargaHoraria() {
        return cargaHoraria;
    }
    
    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
    
    public String getPreRequisitos() {
        return preRequisitos;
    }
    
    public void setPreRequisitos(String preRequisitos) {
        this.preRequisitos = preRequisitos;
    }
    
    public String getCreditos() {
        return creditos;
    }
    
    public void setCreditos(String creditos) {
        this.creditos = creditos;
    }
    
    public String getSemestre() {
        return semestre;
    }
    
    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public boolean isAtiva() {
        return ativa;
    }
    
    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
    
    @Override
    public String toString() {
        return "Disciplina{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", ementa='" + ementa + '\'' +
                ", cargaHoraria=" + cargaHoraria +
                ", preRequisitos='" + preRequisitos + '\'' +
                ", creditos='" + creditos + '\'' +
                ", semestre='" + semestre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", ativa=" + ativa +
                '}';
    }
} 