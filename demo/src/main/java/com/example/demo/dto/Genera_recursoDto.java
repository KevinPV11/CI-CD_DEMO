package com.example.demo.dto;

public class Genera_recursoDto {

    private Long id;
    private String tipoGeneradorRecurso;
    private Long ciudadId;
    private Long recursoGeneradoId;
    private int capacidadGeneracion;

    public Genera_recursoDto(){}

    public Genera_recursoDto(Long id, String tipoGeneradorRecurso, Long ciudadId, Long recursoGeneradoId, int capacidadGeneracion) {
        this.id = id;
        this.tipoGeneradorRecurso = tipoGeneradorRecurso;
        this.ciudadId = ciudadId;
        this.recursoGeneradoId = recursoGeneradoId;
        this.capacidadGeneracion = capacidadGeneracion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoGeneradorRecurso() {
        return tipoGeneradorRecurso;
    }

    public void setTipoGeneradorRecurso(String tipoGeneradorRecurso) {
        this.tipoGeneradorRecurso = tipoGeneradorRecurso;
    }

    public Long getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(Long ciudadId) {
        this.ciudadId = ciudadId;
    }

    public Long getRecursoGeneradoId() {
        return recursoGeneradoId;
    }

    public void setRecursoGeneradoId(Long recursoGeneradoId) {
        this.recursoGeneradoId = recursoGeneradoId;
    }

    public int getCapacidadGeneracion() {
        return capacidadGeneracion;
    }

    public void setCapacidadGeneracion(int capacidadGeneracion) {
        this.capacidadGeneracion = capacidadGeneracion;
    }
}
