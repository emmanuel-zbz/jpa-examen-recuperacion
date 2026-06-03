package org.example.examenjparecuperacion.controller;

import lombok.RequiredArgsConstructor;
import org.example.examenjparecuperacion.domain.Curso;
import org.example.examenjparecuperacion.proyecciones.CursoVista;
import org.example.examenjparecuperacion.service.CursoService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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


    @GetMapping("/buscar")
    public ResponseEntity<List<CursoVista>> buscarCursos(@RequestParam String valor) {
        List<CursoVista> resultados = cursoService.soloBuscar(valor);
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/ordenar")
    public ResponseEntity<List<CursoVista>> ordenarCursos(
            @RequestParam(defaultValue = "precio") String campo,
            @RequestParam(defaultValue = "asc") String direccion) {

        List<CursoVista> resultados = cursoService.soloOrdenar(campo, direccion);
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/paginar")
    public ResponseEntity<Map<String, Object>> paginarCursos(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamano) {

        Page<CursoVista> paginaResultados = cursoService.soloPaginar(pagina, tamano);

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("datos", paginaResultados.getContent());
        respuesta.put("paginaActual", paginaResultados.getNumber());
        respuesta.put("totalElementos", paginaResultados.getTotalElements());
        respuesta.put("totalPaginas", paginaResultados.getTotalPages());

        return ResponseEntity.ok(respuesta);
    }
}