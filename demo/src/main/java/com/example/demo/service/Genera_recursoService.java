package com.example.demo.service;

import com.example.demo.dto.Genera_recursoDto;
import com.example.demo.model.Ciudad;
import com.example.demo.model.Genera_recuso;
import com.example.demo.model.Recurso;
import com.example.demo.model.Tipo_generador_recurso;
import com.example.demo.repository.CiudadRepository;
import com.example.demo.repository.GeneraRecursoRepository;
import com.example.demo.repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Genera_recursoService implements IGenera_recursoService{

    private final GeneraRecursoRepository generaRecursoRepository;
    private final CiudadRepository ciudadRepository; // Repositorio para Ciudad
    private final RecursoRepository recursoRepository; // Repositorio para Recurso

    @Autowired
    public Genera_recursoService(GeneraRecursoRepository generaRecursoRepository,
                                 CiudadRepository ciudadRepository,
                                 RecursoRepository recursoRepository) {
        this.generaRecursoRepository = generaRecursoRepository;
        this.ciudadRepository = ciudadRepository;
        this.recursoRepository = recursoRepository;
    }

    @Override
    public List<Genera_recursoDto> obtenerTodosLosGeneradores() {
        List<Genera_recuso> generadores = generaRecursoRepository.findAll();
        return generadores.stream()
                .map(this::convertirAGeneraRecursoDto)
                .collect(Collectors.toList());
    }

    @Override
    public Genera_recursoDto obtenerGeneradorPorId(Long id) {
        Genera_recuso generador = generaRecursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Generador no encontrado"));
        return convertirAGeneraRecursoDto(generador);
    }

    @Override
    public Genera_recursoDto crearGenerador(Genera_recursoDto generaRecursoDto) {
        Ciudad ciudad = ciudadRepository.findById(generaRecursoDto.getCiudadId())
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
        Recurso recurso = recursoRepository.findById(generaRecursoDto.getRecursoGeneradoId())
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));

        Genera_recuso generador = new Genera_recuso();
        generador.setTipoGeneradorRecurso(Tipo_generador_recurso.valueOf(generaRecursoDto.getTipoGeneradorRecurso()));
        generador.setCiudad(ciudad);
        generador.setRecursoGenerado(recurso);
        generador.setCapacidadGeneracion(generaRecursoDto.getCapacidadGeneracion());

        generaRecursoRepository.save(generador);
        return convertirAGeneraRecursoDto(generador);
    }

    @Override
    public Genera_recursoDto actualizarGenerador(Long id, Genera_recursoDto generaRecursoDto) {
        Genera_recuso generador = generaRecursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Generador no encontrado"));

        Ciudad ciudad = ciudadRepository.findById(generaRecursoDto.getCiudadId())
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
        Recurso recurso = recursoRepository.findById(generaRecursoDto.getRecursoGeneradoId())
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));

        generador.setTipoGeneradorRecurso(Tipo_generador_recurso.valueOf(generaRecursoDto.getTipoGeneradorRecurso()));
        generador.setCiudad(ciudad);
        generador.setRecursoGenerado(recurso);
        generador.setCapacidadGeneracion(generaRecursoDto.getCapacidadGeneracion());

        generaRecursoRepository.save(generador);
        return convertirAGeneraRecursoDto(generador);
    }

    @Override
    public void eliminarGenerador(Long id) {
        generaRecursoRepository.deleteById(id);
    }

    private Genera_recursoDto convertirAGeneraRecursoDto(Genera_recuso generador) {
        return new Genera_recursoDto(
                generador.getId(),
                generador.getTipoGeneradorRecurso().name(),
                generador.getCiudad().getId(),
                generador.getRecursoGenerado().getId(),
                generador.getCapacidadGeneracion()
        );
    }
}
