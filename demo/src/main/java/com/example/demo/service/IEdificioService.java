package com.example.demo.service;

import com.example.demo.dto.EdificioDto;

import java.util.List;
import java.util.Map;

public interface IEdificioService {
    List<EdificioDto> obtenerTodosLosEdificios();
    EdificioDto obtenerEdificioPorId(Long id);
    EdificioDto crearEdificio(EdificioDto edificioDto);
    EdificioDto actualizarEdificio(Long id, EdificioDto edificioDto);
    void eliminarEdificio(Long id);
    boolean verificarRecursosParaConstruir(Long ciudadId, Map<String, Integer> costo);
}
