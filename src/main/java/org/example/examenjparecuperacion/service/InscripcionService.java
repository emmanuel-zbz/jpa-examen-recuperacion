package org.example.examenjparecuperacion.service;

import lombok.RequiredArgsConstructor;
import org.example.examenjparecuperacion.domain.Alumno;
import org.example.examenjparecuperacion.domain.Curso;
import org.example.examenjparecuperacion.dto.AlumnoInscripcionResponseDTO;
import org.example.examenjparecuperacion.dto.CursoInscritoDTO;
import org.example.examenjparecuperacion.repository.AlumnoRepository;
import org.example.examenjparecuperacion.repository.CursoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InscripcionService {

    private final AlumnoRepository alumnoRepository;
    private final CursoRepository cursoRepository;

    @Transactional
    public AlumnoInscripcionResponseDTO inscribirAlumnoEnCurso(Long alumnoId, Long cursoId) {
        Alumno alumno = obtenerAlumnoPorId(alumnoId);
        Curso curso = obtenerCursoPorId(cursoId);

        hacerVinculacion(alumno, curso);

        return construirDTO(alumno);
    }

    private Alumno obtenerAlumnoPorId(Long alumnoId) {
        return alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alumno no encontrado"));
    }

    private Curso obtenerCursoPorId(Long cursoId) {
        return cursoRepository.findById(cursoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));
    }

    private void hacerVinculacion(Alumno alumno, Curso curso) {
        if (curso.getAlumnos().contains(alumno)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El alumno ya esta inscrito");
        }

        curso.getAlumnos().add(alumno);

        alumno.getCursos().add(curso);
    }

    private AlumnoInscripcionResponseDTO construirDTO(Alumno alumno) {
        List<Curso> cursosMatriculados = alumno.getCursos();

        BigDecimal totalInversion = calcularInversionTotal(cursosMatriculados);
        List<CursoInscritoDTO> listadoCursosDto = mapearCursosADto(cursosMatriculados);

        return new AlumnoInscripcionResponseDTO(
                alumno.getUsername(),
                alumno.getEmail(),
                listadoCursosDto,
                totalInversion
        );
    }

    private BigDecimal calcularInversionTotal(List<Curso> cursos) {
        return cursos.stream()
                .map(Curso::getPrecio)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<CursoInscritoDTO> mapearCursosADto(List<Curso> cursos) {
        return cursos.stream()
                .map(c -> new CursoInscritoDTO(c.getTitulo(), c.getResumen(), c.getPrecio()))
                .toList();
    }
}