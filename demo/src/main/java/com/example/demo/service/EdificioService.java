package com.example.demo.service;

import com.example.demo.dto.EdificioDto;
import com.example.demo.model.*;
import com.example.demo.repository.CiudadRepository;
import com.example.demo.repository.EdificioRepository;
import com.example.demo.repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EdificioService implements IEdificioService{

    @Autowired
    private EdificioRepository edificioRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private RecursoRepository recursoRepository;

    @Override
    public List<EdificioDto> obtenerTodosLosEdificios() {
        return edificioRepository.findAll().stream()
                .map(this::convertirAEdificioDto)
                .collect(Collectors.toList());
    }

    @Override
    public EdificioDto obtenerEdificioPorId(Long id) {
        Edificio edificio = edificioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Edificio no encontrado"));
        return convertirAEdificioDto(edificio);
    }

    @Override
    public EdificioDto crearEdificio(EdificioDto edificioDto) {
        Ciudad ciudad = ciudadRepository.findById(edificioDto.getCiudadId())
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        // Verificamos si la ciudad tiene suficientes recursos para construir el edificio.
        if (!verificarRecursosParaConstruir(ciudad.getId(), edificioDto.getCosto())) {
            throw new RuntimeException("Recursos insuficientes para construir el edificio");
        }

        Edificio edificio = new Edificio();
        edificio.setNombre(edificioDto.getNombre());
        edificio.setTipoEdificio(Tipo_edificio.valueOf(edificioDto.getTipoEdificio()));
        edificio.setCosto(convertirACostoTipoRecurso(edificioDto.getCosto()));
        edificio.setConstruccionCompleta(edificioDto.isConstruccionCompleta());
        edificio.setCiudad(ciudad);

        // Restar los recursos necesarios de la ciudad.
        restarRecursosDeLaCiudad(ciudad, edificio.getCosto());

        return convertirAEdificioDto(edificioRepository.save(edificio));
    }

    @Override
    public EdificioDto actualizarEdificio(Long id, EdificioDto edificioDto) {
        Edificio edificio = edificioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Edificio no encontrado"));

        edificio.setNombre(edificioDto.getNombre());
        edificio.setTipoEdificio(Tipo_edificio.valueOf(edificioDto.getTipoEdificio()));
        edificio.setConstruccionCompleta(edificioDto.isConstruccionCompleta());

        return convertirAEdificioDto(edificioRepository.save(edificio));
    }

    @Override
    public void eliminarEdificio(Long id) {
        edificioRepository.deleteById(id);
    }

    @Override
    public boolean verificarRecursosParaConstruir(Long ciudadId, Map<String, Integer> costo) {
        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        for (Map.Entry<String, Integer> entry : costo.entrySet()) {
            Tipo_recurso tipoRecurso = Tipo_recurso.valueOf(entry.getKey());
            Integer cantidadNecesaria = entry.getValue();

            // Verificamos si la ciudad tiene suficientes recursos del tipo requerido.
            Recurso recurso = ciudad.getRecursos().stream()
                    .filter(r -> r.getTipoRecursos() == tipoRecurso)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Recurso no encontrado en la ciudad"));

            if (recurso.getCantidad() < cantidadNecesaria) {
                return false;  // No tiene suficientes recursos.
            }
        }
        return true;  // La ciudad tiene suficientes recursos.
    }

    private void restarRecursosDeLaCiudad(Ciudad ciudad, Map<Tipo_recurso, Integer> costo) {
        for (Map.Entry<Tipo_recurso, Integer> entry : costo.entrySet()) {
            Tipo_recurso tipoRecurso = entry.getKey();
            Integer cantidad = entry.getValue();

            // Restar la cantidad correspondiente de cada recurso.
            Recurso recurso = ciudad.getRecursos().stream()
                    .filter(r -> r.getTipoRecursos() == tipoRecurso)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Recurso no encontrado en la ciudad"));

            recurso.setCantidad(recurso.getCantidad() - cantidad);
            recursoRepository.save(recurso);  // Guardar el recurso actualizado.
        }
    }

    private Map<Tipo_recurso, Integer> convertirACostoTipoRecurso(Map<String, Integer> costoDto) {
        return costoDto.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> Tipo_recurso.valueOf(entry.getKey()),
                        Map.Entry::getValue
                ));
    }

    private EdificioDto convertirAEdificioDto(Edificio edificio) {
        return new EdificioDto(
                edificio.getId(),
                edificio.getNombre(),
                edificio.getTipoEdificio().name(),
                edificio.getCosto().entrySet().stream()
                        .collect(Collectors.toMap(
                                entry -> entry.getKey().name(),
                                Map.Entry::getValue
                        )),
                edificio.isConstruccionCompleta(),
                edificio.getCiudad().getId()
        );
    }
}
