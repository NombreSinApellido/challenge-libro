package com.aluracursos.challenge_libro.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name")String nombre,
        @JsonAlias("birth_year")String nacimiento,
        @JsonAlias("death_year")String muerte) {
}
