package org.example.examenjparecuperacion.dto;

import java.math.BigDecimal;
import java.util.List;

public record AlumnoInscripcionResponseDTO(
        String username,

        String email,

        List<CursoInscritoDTO> cursos,


        BigDecimal totalInversion
) {
}