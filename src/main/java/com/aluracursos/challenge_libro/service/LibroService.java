package com.aluracursos.challenge_libro.service;

import com.aluracursos.challenge_libro.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {
    @Autowired
    private LibroRepository repository;
}
