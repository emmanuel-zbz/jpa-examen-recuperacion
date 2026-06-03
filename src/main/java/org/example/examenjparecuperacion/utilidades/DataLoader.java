package org.example.examenjparecuperacion.utilidades;

import lombok.RequiredArgsConstructor;
import org.example.examenjparecuperacion.domain.Alumno;
import org.example.examenjparecuperacion.domain.Curso;
import org.example.examenjparecuperacion.domain.Nivel;
import org.example.examenjparecuperacion.repository.AlumnoRepository;
import org.example.examenjparecuperacion.repository.CursoRepository;
import org.example.examenjparecuperacion.repository.NivelRepository; // Tendrás que crear este repositorio
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final AlumnoRepository alumnoRepository;
    private final CursoRepository cursoRepository;
    private final NivelRepository nivelRepository;

    @Override
    public void run(String... args) throws Exception {
        if (nivelRepository.count() == 0) {
            Nivel n1 = new Nivel(null, "Básico", "Nivel inicial", null);
            Nivel n2 = new Nivel(null, "Intermedio", "Nivel medio", null);
            Nivel n3 = new Nivel(null, "Avanzado", "Nivel experto", null);
            nivelRepository.saveAll(List.of(n1, n2, n3));

            Alumno a1 = new Alumno(null, "a1@a.com", "123", "User1", null);
            alumnoRepository.saveAll(List.of(a1));

            Curso c1 = new Curso(null, "Java 1", "Resumen 1", new BigDecimal("50"), 10, n1, null);
            cursoRepository.saveAll(List.of(c1));
        }
    }
}