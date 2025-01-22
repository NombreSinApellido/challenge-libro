package com.aluracursos.challenge_libro;

import com.aluracursos.challenge_libro.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLibroApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeLibroApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.muestraMenu();
	}
}
