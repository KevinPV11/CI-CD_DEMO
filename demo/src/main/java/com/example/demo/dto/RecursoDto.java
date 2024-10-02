package com.example.demo.dto;

public class RecursoDto {

    private Long id;
    private String tipoRecursos;
    private int cantidad;
    private Long ciudadId;

    public RecursoDto(){}

    public RecursoDto(Long id, String tipoRecursos, int cantidad, Long ciudadId) {
        this.id = id;
        this.tipoRecursos = tipoRecursos;
        this.cantidad = cantidad;
        this.ciudadId = ciudadId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoRecursos() {
        return tipoRecursos;
    }

    public void setTipoRecursos(String tipoRecursos) {
        this.tipoRecursos = tipoRecursos;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Long getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(Long ciudadId) {
        this.ciudadId = ciudadId;
    }
}
