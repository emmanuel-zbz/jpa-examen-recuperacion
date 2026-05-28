package org.example.examenjparecuperacion.repository;

import org.example.examenjparecuperacion.domain.Alumno;
import org.example.examenjparecuperacion.domain.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

}
