package com.aluracursos.challenge_libro.repository;

import com.aluracursos.challenge_libro.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}
