package com.example.demo.controller;

import com.example.demo.dto.Genera_recursoDto;
import com.example.demo.service.Genera_recursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genera-recursos")
public class Genera_recursoController {

    private final Genera_recursoService generaRecursoService;

    @Autowired
    public Genera_recursoController(Genera_recursoService generaRecursoService) {
        this.generaRecursoService = generaRecursoService;
    }

    // Obtener todos los generadores
    @GetMapping
    public ResponseEntity<List<Genera_recursoDto>> obtenerTodosLosGeneradores() {
        List<Genera_recursoDto> generadores = generaRecursoService.obtenerTodosLosGeneradores();
        return ResponseEntity.ok(generadores);
    }

    // Obtener un generador por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Genera_recursoDto> obtenerGeneradorPorId(@PathVariable Long id) {
        Genera_recursoDto generador = generaRecursoService.obtenerGeneradorPorId(id);
        return ResponseEntity.ok(generador);
    }

    // Crear un nuevo generador
    @PostMapping
    public ResponseEntity<Genera_recursoDto> crearGenerador(@RequestBody Genera_recursoDto generaRecursoDto) {
        Genera_recursoDto nuevoGenerador = generaRecursoService.crearGenerador(generaRecursoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoGenerador);
    }

    // Actualizar un generador existente
    @PutMapping("/{id}")
    public ResponseEntity<Genera_recursoDto> actualizarGenerador(@PathVariable Long id, @RequestBody Genera_recursoDto generaRecursoDto) {
        Genera_recursoDto generadorActualizado = generaRecursoService.actualizarGenerador(id, generaRecursoDto);
        return ResponseEntity.ok(generadorActualizado);
    }

    // Eliminar un generador por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGenerador(@PathVariable Long id) {
        generaRecursoService.eliminarGenerador(id);
        return ResponseEntity.noContent().build();
    }
}
