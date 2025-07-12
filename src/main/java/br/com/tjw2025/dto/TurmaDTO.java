package br.com.tjw2025.dto;

public class TurmaDTO {
    private Long id;
    private String codigo;
    private DisciplinaDTO disciplina;
    private ProfessorDTO professor;
    private Integer vagas;
    private Integer vagasOcupadas;
    private String diaSemana;
    private String horarioInicio;
    private String horarioFim;
    private String sala;
    private String dataInicio;
    private String dataFim;
    private Boolean ativa;
    private Integer vagasDisponiveis;

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public DisciplinaDTO getDisciplina() { return disciplina; }
    public void setDisciplina(DisciplinaDTO disciplina) { this.disciplina = disciplina; }
    public ProfessorDTO getProfessor() { return professor; }
    public void setProfessor(ProfessorDTO professor) { this.professor = professor; }
    public Integer getVagas() { return vagas; }
    public void setVagas(Integer vagas) { this.vagas = vagas; }
    public Integer getVagasOcupadas() { return vagasOcupadas; }
    public void setVagasOcupadas(Integer vagasOcupadas) { this.vagasOcupadas = vagasOcupadas; }
    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }
    public String getHorarioInicio() { return horarioInicio; }
    public void setHorarioInicio(String horarioInicio) { this.horarioInicio = horarioInicio; }
    public String getHorarioFim() { return horarioFim; }
    public void setHorarioFim(String horarioFim) { this.horarioFim = horarioFim; }
    public String getSala() { return sala; }
    public void setSala(String sala) { this.sala = sala; }
    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }
    public String getDataFim() { return dataFim; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }
    public Boolean getAtiva() { return ativa; }
    public void setAtiva(Boolean ativa) { this.ativa = ativa; }
    public Integer getVagasDisponiveis() { return vagasDisponiveis; }
    public void setVagasDisponiveis(Integer vagasDisponiveis) { this.vagasDisponiveis = vagasDisponiveis; }
} 