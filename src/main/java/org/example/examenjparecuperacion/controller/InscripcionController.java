package org.example.examenjparecuperacion.controller;

import lombok.RequiredArgsConstructor;
import org.example.examenjparecuperacion.dto.AlumnoInscripcionResponseDTO;
import org.example.examenjparecuperacion.service.InscripcionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/examen")
@RequiredArgsConstructor
public class InscripcionController {

    private final InscripcionService inscripcionService;


    @PostMapping("/inscribir/alumno/{alumnoId}/curso/{cursoId}")
    public ResponseEntity<AlumnoInscripcionResponseDTO> inscribir(
            @PathVariable Long alumnoId,
            @PathVariable Long cursoId) {

        AlumnoInscripcionResponseDTO response = inscripcionService.inscribirAlumnoEnCurso(alumnoId, cursoId);

        return ResponseEntity.ok(response);
    }

}
