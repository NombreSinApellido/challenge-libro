package com.aluracursos.challenge_libro.repository;

import com.aluracursos.challenge_libro.model.DatosGenerales;
import com.aluracursos.challenge_libro.model.DatosLibro;
import com.aluracursos.challenge_libro.model.Libro;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String datosLibro);
}
