package com.example.demo.service;

import com.example.demo.dto.CiudadDto;
import com.example.demo.model.*;
import com.example.demo.repository.CiudadRepository;
import com.example.demo.repository.EdificioRepository;
import com.example.demo.repository.GeneraRecursoRepository;
import com.example.demo.repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CiudadService implements ICiudadService{

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private RecursoRepository recursoRepository;

    @Autowired
    private GeneraRecursoRepository generaRecursoRepository;

    @Autowired
    private EdificioRepository edificioRepository;

    @Override
    public List<CiudadDto> obtenerTodasLasCiudades() {
        return ciudadRepository.findAll().stream()
                .map(this::convertirACiudadDto)
                .collect(Collectors.toList());
    }

    @Override
    public CiudadDto obtenerCiudadPorId(Long id) {
        Ciudad ciudad = ciudadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
        return convertirACiudadDto(ciudad);
    }

    @Override
    public CiudadDto crearCiudad(CiudadDto ciudadDto) {
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre(ciudadDto.getNombre());
        // Aquí puedes manejar los recursos, generadores y edificios si es necesario.
        return convertirACiudadDto(ciudadRepository.save(ciudad));
    }

    @Override
    public CiudadDto actualizarCiudad(Long id, CiudadDto ciudadDto) {
        Ciudad ciudad = ciudadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
        ciudad.setNombre(ciudadDto.getNombre());
        return convertirACiudadDto(ciudadRepository.save(ciudad));
    }

    @Override
    public void eliminarCiudad(Long id) {
        ciudadRepository.deleteById(id);
    }

    @Override
    public void agregarRecursoAlaCiudad(Long ciudadId, Long recursoId) {
        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
        Recurso recurso = recursoRepository.findById(recursoId)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        ciudad.getRecursos().add(recurso);
        ciudadRepository.save(ciudad);
    }

    @Override
    public void agregarGeneradorAlaCiudad(Long ciudadId, Long generaRecursoId) {
        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
        Genera_recuso generaRecurso = generaRecursoRepository.findById(generaRecursoId)
                .orElseThrow(() -> new RuntimeException("Generador no encontrado"));
        ciudad.getGeneraRecusos().add(generaRecurso);
        ciudadRepository.save(ciudad);
    }

    @Override
    public void agregarEdificioAlaCiudad(Long ciudadId, Long edificioId) {
        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        Edificio edificio = edificioRepository.findById(edificioId)
                .orElseThrow(() -> new RuntimeException("Edificio no encontrado"));

        // Validar si la ciudad tiene los recursos necesarios para construir el edificio
        Map<Tipo_recurso, Integer> costoEdificio = edificio.getCosto();
        for (Map.Entry<Tipo_recurso, Integer> entry : costoEdificio.entrySet()) {
            Tipo_recurso tipoRecurso = entry.getKey();
            int cantidadNecesaria = entry.getValue();

            // Encontrar el recurso en la ciudad
            Recurso recursoCiudad = ciudad.getRecursos().stream()
                    .filter(recurso -> recurso.getTipoRecursos() == tipoRecurso)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No hay suficiente recurso de tipo: " + tipoRecurso));

            // Verificar si la ciudad tiene suficiente cantidad de ese recurso
            if (recursoCiudad.getCantidad() < cantidadNecesaria) {
                throw new RuntimeException("No hay suficientes recursos para construir el edificio");
            }

            // Si hay suficiente, restar la cantidad de recursos utilizada
            recursoCiudad.setCantidad(recursoCiudad.getCantidad() - cantidadNecesaria);
        }

        // Una vez descontados los recursos, marcar la construcción del edificio como completa y añadirlo a la ciudad
        edificio.setConstruccionCompleta(true);
        edificio.setCiudad(ciudad);

        ciudad.getEdificios().add(edificio);
        ciudadRepository.save(ciudad);
    }

    private CiudadDto convertirACiudadDto(Ciudad ciudad) {
        return new CiudadDto(
                ciudad.getId(),
                ciudad.getNombre(),
                ciudad.getRecursos().stream().map(Recurso::getId).collect(Collectors.toList()),
                ciudad.getGeneraRecusos().stream().map(Genera_recuso::getId).collect(Collectors.toList()),
                // Aquí deberías mapear los IDs de los edificios también.
                ciudad.getEdificios().stream().map(edificio -> edificio.getId()).collect(Collectors.toList())
        );
    }
}
