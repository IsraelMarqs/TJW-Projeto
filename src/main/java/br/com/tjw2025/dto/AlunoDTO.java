package br.com.tjw2025.dto;

import java.util.List;

public class AlunoDTO extends BaseDTO {
    private String dataMatricula;
    private List<MatriculaDTO> matriculas;

    // Getters e setters específicos do AlunoDTO
    public String getDataMatricula() { return dataMatricula; }
    public void setDataMatricula(String dataMatricula) { this.dataMatricula = dataMatricula; }
    public List<MatriculaDTO> getMatriculas() { return matriculas; }
    public void setMatriculas(List<MatriculaDTO> matriculas) { this.matriculas = matriculas; }
} 