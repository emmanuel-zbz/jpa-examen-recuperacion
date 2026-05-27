package org.example.examenjparecuperacion.service;

import lombok.RequiredArgsConstructor;
import org.example.examenjparecuperacion.domain.Curso;
import org.example.examenjparecuperacion.repository.CursoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;

    public Curso save(Curso curso) {
        return cursoRepository.save(curso);
    }


    @Transactional(readOnly = true)
    public List<Curso> getAllCursos() {
        return cursoRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Curso getCurso(Long id) {
        return cursoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el curso"));
    }

    public Curso updateCurso(Long id, Curso curso) {
        return cursoRepository.findById(id).map(c -> {
            c.setResumen(curso.getResumen());
            c.setAlumnos(curso.getAlumnos());
            c.setPrecio(curso.getPrecio());
            c.setNivel(curso.getNivel());
            c.setHoras(curso.getHoras());
            c.setTitulo(curso.getTitulo());
            c.setResumen(curso.getResumen());

            return cursoRepository.save(c);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el curso"));
    }

    public void deleteCurso(Long id) {
        cursoRepository.deleteById(id);
    }

}
