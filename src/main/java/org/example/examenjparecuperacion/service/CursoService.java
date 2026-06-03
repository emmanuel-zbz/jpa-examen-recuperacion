package org.example.examenjparecuperacion.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.examenjparecuperacion.domain.Curso;
import org.example.examenjparecuperacion.proyecciones.CursoVista;
import org.example.examenjparecuperacion.repository.CursoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado el curso con ID: " + id));
    }

    public Curso updateCurso(Long id, Curso curso) {
        Curso cursoExistente = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado el curso con ID: " + id));

        cursoExistente.setResumen(curso.getResumen());
        cursoExistente.setAlumnos(curso.getAlumnos());
        cursoExistente.setPrecio(curso.getPrecio());
        cursoExistente.setNivel(curso.getNivel());
        cursoExistente.setHoras(curso.getHoras());
        cursoExistente.setTitulo(curso.getTitulo());

        return cursoExistente;
    }

    public void deleteCurso(Long id) {
        cursoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<CursoVista> soloBuscar(String busqueda) {
        return cursoRepository.findByTituloContainingIgnoreCase(busqueda);
    }

    @Transactional(readOnly = true)
    public List<CursoVista> soloOrdenar(String campo, String direccion) {
        Sort.Direction dir = direccion.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(dir, campo);

        return cursoRepository.findAllProjectedBy(sort);
    }

    @Transactional(readOnly = true)
    public Page<CursoVista> soloPaginar(int pagina, int tamano) {
        Pageable pageable = PageRequest.of(pagina, tamano);

        return cursoRepository.findAllProjectedBy(pageable);
    }
}