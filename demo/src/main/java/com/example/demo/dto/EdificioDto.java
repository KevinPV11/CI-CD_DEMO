package com.example.demo.dto;

import java.util.Map;

public class EdificioDto {
    private Long id;
    private String nombre;
    private String TipoEdificio;
    private Map<String, Integer> costo;
    private boolean construccionCompleta;
    private Long ciudadId;

    public EdificioDto(){}

    public EdificioDto(Long id, String nombre, String tipoEdificio, Map<String, Integer> costo, boolean construccionCompleta, Long ciudadId) {
        this.id = id;
        this.nombre = nombre;
        TipoEdificio = tipoEdificio;
        this.costo = costo;
        this.construccionCompleta = construccionCompleta;
        this.ciudadId = ciudadId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoEdificio() {
        return TipoEdificio;
    }

    public void setTipoEdificio(String tipoEdificio) {
        TipoEdificio = tipoEdificio;
    }

    public Map<String, Integer> getCosto() {
        return costo;
    }

    public void setCosto(Map<String, Integer> costo) {
        this.costo = costo;
    }

    public boolean isConstruccionCompleta() {
        return construccionCompleta;
    }

    public void setConstruccionCompleta(boolean construccionCompleta) {
        this.construccionCompleta = construccionCompleta;
    }

    public Long getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(Long ciudadId) {
        this.ciudadId = ciudadId;
    }
}
