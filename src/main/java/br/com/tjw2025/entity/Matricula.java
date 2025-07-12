package br.com.tjw2025.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "matriculas")
public class Matricula {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    @NotNull(message = "Aluno é obrigatório")
    private Aluno aluno;
    
    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = false)
    @NotNull(message = "Turma é obrigatória")
    private Turma turma;
    
    @NotNull(message = "Data de matrícula é obrigatória")
    private LocalDate dataMatricula;
    
    @NotNull(message = "Data de criação é obrigatória")
    private LocalDateTime dataCriacao;
    
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status é obrigatório")
    private StatusMatricula status = StatusMatricula.ATIVA;
    
    private String observacoes;
    
    // Enum para status da matrícula
    public enum StatusMatricula {
        ATIVA("Ativa"),
        CANCELADA("Cancelada"),
        TRANCADA("Trancada"),
        CONCLUIDA("Concluída");
        
        private final String descricao;
        
        StatusMatricula(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    // Construtores
    public Matricula() {
        this.dataCriacao = LocalDateTime.now();
    }
    
    public Matricula(Aluno aluno, Turma turma, LocalDate dataMatricula) {
        this();
        this.aluno = aluno;
        this.turma = turma;
        this.dataMatricula = dataMatricula;
    }
    
    public Matricula(Aluno aluno, Turma turma, LocalDate dataMatricula, String observacoes) {
        this(aluno, turma, dataMatricula);
        this.observacoes = observacoes;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Aluno getAluno() {
        return aluno;
    }
    
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
    
    public Turma getTurma() {
        return turma;
    }
    
    public void setTurma(Turma turma) {
        this.turma = turma;
    }
    
    public LocalDate getDataMatricula() {
        return dataMatricula;
    }
    
    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    public StatusMatricula getStatus() {
        return status;
    }
    
    public void setStatus(StatusMatricula status) {
        this.status = status;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    // Métodos de negócio
    public boolean isAtiva() {
        return StatusMatricula.ATIVA.equals(status);
    }
    
    public boolean isCancelada() {
        return StatusMatricula.CANCELADA.equals(status);
    }
    
    public boolean isTrancada() {
        return StatusMatricula.TRANCADA.equals(status);
    }
    
    public boolean isConcluida() {
        return StatusMatricula.CONCLUIDA.equals(status);
    }
    
    public void cancelar() {
        this.status = StatusMatricula.CANCELADA;
    }
    
    public void trancar() {
        this.status = StatusMatricula.TRANCADA;
    }
    
    public void concluir() {
        this.status = StatusMatricula.CONCLUIDA;
    }
    
    public void reativar() {
        this.status = StatusMatricula.ATIVA;
    }
    
    @Override
    public String toString() {
        return "Matricula{" +
                "id=" + id +
                ", aluno=" + (aluno != null ? aluno.getNome() : "null") +
                ", turma=" + (turma != null ? turma.getCodigo() : "null") +
                ", dataMatricula=" + dataMatricula +
                ", dataCriacao=" + dataCriacao +
                ", status=" + status +
                ", observacoes='" + observacoes + '\'' +
                '}';
    }
} 