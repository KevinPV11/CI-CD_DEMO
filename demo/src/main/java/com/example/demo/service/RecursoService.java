package com.example.demo.service;

import com.example.demo.dto.RecursoDto;
import com.example.demo.model.Ciudad;
import com.example.demo.model.Recurso;
import com.example.demo.model.Tipo_recurso;
import com.example.demo.repository.CiudadRepository;
import com.example.demo.repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecursoService implements IRecursoService{
    @Autowired
    private RecursoRepository recursoRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Override
    public List<RecursoDto> obtenerTodosLosRecursos() {
        return recursoRepository.findAll().stream()
                .map(this::convertirARecursoDto)
                .collect(Collectors.toList());
    }

    @Override
    public RecursoDto obtenerRecursoPorId(Long id) {
        Recurso recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        return convertirARecursoDto(recurso);
    }

    @Override
    public RecursoDto crearRecurso(RecursoDto recursoDto) {
        Ciudad ciudad = ciudadRepository.findById(recursoDto.getCiudadId())
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        Recurso recurso = new Recurso();
        recurso.setTipoRecursos(Tipo_recurso.valueOf(recursoDto.getTipoRecursos()));
        recurso.setCantidad(recursoDto.getCantidad());
        recurso.setCiudad(ciudad);

        return convertirARecursoDto(recursoRepository.save(recurso));
    }

    @Override
    public RecursoDto actualizarRecurso(Long id, RecursoDto recursoDto) {
        Recurso recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));

        recurso.setTipoRecursos(Tipo_recurso.valueOf(recursoDto.getTipoRecursos()));
        recurso.setCantidad(recursoDto.getCantidad());

        return convertirARecursoDto(recursoRepository.save(recurso));
    }

    @Override
    public void eliminarRecurso(Long id) {
        recursoRepository.deleteById(id);
    }

    private RecursoDto convertirARecursoDto(Recurso recurso) {
        return new RecursoDto(
                recurso.getId(),
                recurso.getTipoRecursos().name(),
                recurso.getCantidad(),
                recurso.getCiudad().getId()
        );
    }
}
