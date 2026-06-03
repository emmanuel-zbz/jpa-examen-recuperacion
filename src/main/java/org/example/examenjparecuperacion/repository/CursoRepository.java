package org.example.examenjparecuperacion.repository;

import org.example.examenjparecuperacion.domain.Curso;
import org.example.examenjparecuperacion.proyecciones.CursoVista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    Page<CursoVista> findAllProjectedBy(Pageable pageable);

    List<CursoVista> findAllProjectedBy(Sort sort);

    List<CursoVista> findByTituloContainingIgnoreCase(String titulo);
}