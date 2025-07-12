package br.com.tjw2025.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "turmas")
public class Turma {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Código da turma é obrigatório")
    @Column(unique = true)
    private String codigo;
    
    @ManyToOne
    @JoinColumn(name = "disciplina_id", nullable = false)
    @NotNull(message = "Disciplina é obrigatória")
    private Disciplina disciplina;
    
    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    @NotNull(message = "Professor é obrigatório")
    private Professor professor;
    
    @NotNull(message = "Vagas é obrigatório")
    @Min(value = 1, message = "Vagas deve ser maior que 0")
    private Integer vagas;
    
    @NotNull(message = "Vagas ocupadas é obrigatório")
    @Min(value = 0, message = "Vagas ocupadas não pode ser negativo")
    private Integer vagasOcupadas = 0;
    
    @NotBlank(message = "Dia da semana é obrigatório")
    private String diaSemana;
    
    @NotNull(message = "Horário de início é obrigatório")
    private LocalTime horarioInicio;
    
    @NotNull(message = "Horário de fim é obrigatório")
    private LocalTime horarioFim;
    
    @NotBlank(message = "Sala é obrigatória")
    private String sala;
    
    @NotNull(message = "Data de início é obrigatória")
    private LocalDate dataInicio;
    
    @NotNull(message = "Data de fim é obrigatória")
    private LocalDate dataFim;
    
    private boolean ativa = true;
    
    // Construtores
    public Turma() {}
    
    public Turma(String codigo, Disciplina disciplina, Professor professor, 
                 Integer vagas, String diaSemana, LocalTime horarioInicio, LocalTime horarioFim, 
                 String sala, LocalDate dataInicio, LocalDate dataFim) {
        this.codigo = codigo;
        this.disciplina = disciplina;
        this.professor = professor;
        this.vagas = vagas;
        this.diaSemana = diaSemana;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.sala = sala;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
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
    
    public Disciplina getDisciplina() {
        return disciplina;
    }
    
    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
    
    public Professor getProfessor() {
        return professor;
    }
    
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
    
    public Integer getVagas() {
        return vagas;
    }
    
    public void setVagas(Integer vagas) {
        this.vagas = vagas;
    }
    
    public Integer getVagasOcupadas() {
        return vagasOcupadas;
    }
    
    public void setVagasOcupadas(Integer vagasOcupadas) {
        this.vagasOcupadas = vagasOcupadas;
    }
    
    public String getDiaSemana() {
        return diaSemana;
    }
    
    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }
    
    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }
    
    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }
    
    public LocalTime getHorarioFim() {
        return horarioFim;
    }
    
    public void setHorarioFim(LocalTime horarioFim) {
        this.horarioFim = horarioFim;
    }
    
    public String getSala() {
        return sala;
    }
    
    public void setSala(String sala) {
        this.sala = sala;
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
    
    public boolean isAtiva() {
        return ativa;
    }
    
    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
    
    public Integer getVagasDisponiveis() {
        return vagas - vagasOcupadas;
    }
    
    public boolean temVagasDisponiveis() {
        return vagas != null && vagasOcupadas != null && vagasOcupadas < vagas;
    }
    
    public void incrementarVagasOcupadas() {
        if (temVagasDisponiveis()) {
            this.vagasOcupadas++;
        }
    }
    
    public void decrementarVagasOcupadas() {
        if (this.vagasOcupadas > 0) {
            this.vagasOcupadas--;
        }
    }
    
    @Override
    public String toString() {
        return "Turma{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", disciplina=" + disciplina +
                ", professor=" + professor +
                ", vagas=" + vagas +
                ", vagasOcupadas=" + vagasOcupadas +
                ", diaSemana='" + diaSemana + '\'' +
                ", horarioInicio=" + horarioInicio +
                ", horarioFim=" + horarioFim +
                ", sala='" + sala + '\'' +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", ativa=" + ativa +
                '}';
    }
} 