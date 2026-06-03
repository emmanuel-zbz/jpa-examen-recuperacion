package org.example.examenjparecuperacion.repository;

import org.example.examenjparecuperacion.domain.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NivelRepository extends JpaRepository<Nivel, Long> {
}