package org.example.examenjparecuperacion.controller;

import lombok.RequiredArgsConstructor;
import org.example.examenjparecuperacion.domain.Curso;
import org.example.examenjparecuperacion.service.CursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/examen/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @PostMapping
    public ResponseEntity<Curso> saveCurso(@RequestBody Curso curso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.save(curso));
    }

    @GetMapping
    public ResponseEntity<List<Curso>> getCursos() {
        return ResponseEntity.ok(cursoService.getAllCursos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> getCurso(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.getCurso(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> updateCurso(@RequestBody Curso curso, @PathVariable Long id) {
        return ResponseEntity.ok(cursoService.updateCurso(id, curso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        cursoService.deleteCurso(id);
        return ResponseEntity.noContent().build();
    }

}
