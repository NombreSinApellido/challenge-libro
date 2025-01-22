package com.aluracursos.challenge_libro.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> tClass);
}
