package org.example.examenjparecuperacion.dto;

import java.math.BigDecimal;

public record CursoInscritoDTO(
        String titulo,
        String resumen,
        BigDecimal precio
) {}