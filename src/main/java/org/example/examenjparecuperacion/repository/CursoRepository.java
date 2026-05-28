package org.example.examenjparecuperacion.repository;

import org.example.examenjparecuperacion.domain.Curso;
import org.example.examenjparecuperacion.proyecciones.CursoVista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {


    Page<CursoVista> findAllProjectedBy(Pageable pageable);


    Page<CursoVista> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);


    Page<CursoVista> findByResumenContainingIgnoreCase(String resumen, Pageable pageable);

    Page<CursoVista> findByPrecio(BigDecimal precio, Pageable pageable);
}
