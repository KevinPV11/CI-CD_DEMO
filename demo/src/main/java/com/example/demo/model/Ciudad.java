package com.example.demo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "ciudad")
@Table(name = "ciudades")
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "ciudad")
    private List<Recurso> recursos;

    @OneToMany(mappedBy = "ciudad")
    private List<Genera_recuso> generaRecusos;

    @OneToMany(mappedBy = "ciudad")
    private List<Edificio> edificios;

    public Ciudad(){}

    public Ciudad(String nombre, List<Recurso> recursos, List<Genera_recuso> generaRecusos, List<Edificio> edificios) {
        this.nombre = nombre;
        this.recursos = recursos;
        this.generaRecusos = generaRecusos;
        this.edificios = edificios;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public List<Recurso> getRecursos(){
        return recursos;
    }

    public void setRecursos(List<Recurso> recursos){
        this.recursos = recursos;
    }

    public List<Genera_recuso> getGeneraRecusos(){
        return generaRecusos;
    }

    public void setGenerarecusos(List<Genera_recuso> generaRecusos){
        this.generaRecusos = generaRecusos;
    }

    public List<Edificio> getEdificios(){
        return edificios;
    }

    public void setEdificios(List<Edificio> edificios){
        this.edificios = edificios;
    }
}
