package br.com.tjw2025.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "semestres")
public class Semestre {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Ano é obrigatório")
    @Pattern(regexp = "\\d{4}", message = "Ano deve conter 4 dígitos")
    private String ano;
    
    @NotBlank(message = "Período é obrigatório")
    @Pattern(regexp = "[1-2]", message = "Período deve ser 1 ou 2")
    private String periodo;
    
    @NotNull(message = "Data de início é obrigatória")
    private LocalDate dataInicio;
    
    @NotNull(message = "Data de fim é obrigatória")
    private LocalDate dataFim;
    
    @NotNull(message = "Data de início das matrículas é obrigatória")
    private LocalDate dataInicioMatriculas;
    
    @NotNull(message = "Data de fim das matrículas é obrigatória")
    private LocalDate dataFimMatriculas;
    
    private boolean ativo = true;
    
    // Construtores
    public Semestre() {}
    
    public Semestre(String ano, String periodo, LocalDate dataInicio, LocalDate dataFim, 
                   LocalDate dataInicioMatriculas, LocalDate dataFimMatriculas) {
        this.ano = ano;
        this.periodo = periodo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.dataInicioMatriculas = dataInicioMatriculas;
        this.dataFimMatriculas = dataFimMatriculas;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getAno() {
        return ano;
    }
    
    public void setAno(String ano) {
        this.ano = ano;
    }
    
    public String getPeriodo() {
        return periodo;
    }
    
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
    public LocalDate getDataInicio() {
        return dataInicio;
    }
    
    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }
    
    public LocalDate getDataFim() {
        return dataFim;
    }
    
    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }
    
    public LocalDate getDataInicioMatriculas() {
        return dataInicioMatriculas;
    }
    
    public void setDataInicioMatriculas(LocalDate dataInicioMatriculas) {
        this.dataInicioMatriculas = dataInicioMatriculas;
    }
    
    public LocalDate getDataFimMatriculas() {
        return dataFimMatriculas;
    }
    
    public void setDataFimMatriculas(LocalDate dataFimMatriculas) {
        this.dataFimMatriculas = dataFimMatriculas;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    public String getDescricao() {
        return ano + "." + periodo;
    }
    
    @Override
    public String toString() {
        return "Semestre{" +
                "id=" + id +
                ", ano='" + ano + '\'' +
                ", periodo='" + periodo + '\'' +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", dataInicioMatriculas=" + dataInicioMatriculas +
                ", dataFimMatriculas=" + dataFimMatriculas +
                ", ativo=" + ativo +
                '}';
    }
} 