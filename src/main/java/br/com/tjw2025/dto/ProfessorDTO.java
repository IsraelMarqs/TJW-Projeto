package br.com.tjw2025.dto;

public class ProfessorDTO extends BaseDTO {
    private String areaAtuacao;
    private String titulacao;
    private String dataContratacao;

    // Getters e setters específicos do ProfessorDTO
    public String getAreaAtuacao() { return areaAtuacao; }
    public void setAreaAtuacao(String areaAtuacao) { this.areaAtuacao = areaAtuacao; }
    public String getTitulacao() { return titulacao; }
    public void setTitulacao(String titulacao) { this.titulacao = titulacao; }
    public String getDataContratacao() { return dataContratacao; }
    public void setDataContratacao(String dataContratacao) { this.dataContratacao = dataContratacao; }
} 