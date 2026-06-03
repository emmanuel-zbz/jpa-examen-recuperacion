package org.example.examenjparecuperacion.mapper;

import org.example.examenjparecuperacion.domain.Alumno;
import org.example.examenjparecuperacion.domain.Curso;
import org.example.examenjparecuperacion.dto.AlumnoInscripcionResponseDTO;
import org.example.examenjparecuperacion.dto.CursoInscritoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InscripcionMapper {


    @Mapping(target = "totalInversion", source = "totalInversion")
    AlumnoInscripcionResponseDTO toResponseDto(Alumno alumno, BigDecimal totalInversion);

    CursoInscritoDTO toCursoDto(Curso curso);
}