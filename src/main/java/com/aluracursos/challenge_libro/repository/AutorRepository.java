package com.aluracursos.challenge_libro.repository;

import com.aluracursos.challenge_libro.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepository extends JpaRepository <Autor, Long> {
    Optional<Autor> findByNombre(String nombre);
}
