package org.example.examenjparecuperacion.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.examenjparecuperacion.domain.Alumno;
import org.example.examenjparecuperacion.domain.Curso;
import org.example.examenjparecuperacion.dto.AlumnoInscripcionResponseDTO;
import org.example.examenjparecuperacion.mapper.InscripcionMapper;
import org.example.examenjparecuperacion.repository.AlumnoRepository;
import org.example.examenjparecuperacion.repository.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InscripcionService {

    private final AlumnoRepository alumnoRepository;
    private final CursoRepository cursoRepository;

    private final InscripcionMapper inscripcionMapper;

    @Transactional
    public AlumnoInscripcionResponseDTO inscribirAlumnoEnCurso(Long alumnoId, Long cursoId) {
        Alumno alumno = obtenerAlumnoPorId(alumnoId);
        Curso curso = obtenerCursoPorId(cursoId);

        hacerVinculacion(alumno, curso);

        BigDecimal totalInversion = calcularInversionTotal(alumno.getCursos());

        return inscripcionMapper.toResponseDto(alumno, totalInversion);
    }

    private Alumno obtenerAlumnoPorId(Long alumnoId) {
        return alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new EntityNotFoundException("Alumno no encontrado con ID: " + alumnoId));
    }

    private Curso obtenerCursoPorId(Long cursoId) {
        return cursoRepository.findById(cursoId)
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado con ID: " + cursoId));
    }

    private void hacerVinculacion(Alumno alumno, Curso curso) {
        if (curso.getAlumnos().contains(alumno)) {
            throw new IllegalStateException("El alumno ya esta inscrito en este curso");
        }

        curso.getAlumnos().add(alumno);
        alumno.getCursos().add(curso);
    }

    private BigDecimal calcularInversionTotal(List<Curso> cursos) {
        return cursos.stream()
                .map(Curso::getPrecio)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}