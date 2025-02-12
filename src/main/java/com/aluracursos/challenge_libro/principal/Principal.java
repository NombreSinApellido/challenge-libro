package com.aluracursos.challenge_libro.principal;

import com.aluracursos.challenge_libro.model.Autor;
import com.aluracursos.challenge_libro.model.DatosGenerales;
import com.aluracursos.challenge_libro.model.DatosLibro;
import com.aluracursos.challenge_libro.model.Libro;
import com.aluracursos.challenge_libro.repository.AutorRepository;
import com.aluracursos.challenge_libro.repository.LibroRepository;
import com.aluracursos.challenge_libro.service.ConsumoAPI;
import com.aluracursos.challenge_libro.service.ConvierteDatos;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    @Autowired
    private LibroRepository repository;
    @Autowired
    private AutorRepository autorRepository;
    private Optional<DatosLibro> libroBuscado;
    private List<Libro> libros;

    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por título
                    2 - Mostrar todos los libros guardados
                    3 - Mostrar series buscadas
                    4 - Buscar series por título
                    5 - Top 5 mejores series
                    6 - Buscar Series por categoria
                    7 - Filtrar por número de temporadas y calificación
                    8 - Buscar episodios por titulo
                    9 - Top 5 episodios por serie

                    0 - Salir
                    """;

            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    mostrarTodosTitulos();
                    break;
                default:
                    break;
            }
        }
    }

    public void getDatosLibro(String nombreLibro) {
        // System.out.println("Escriba el nombre del libro que desea buscar: ");
        // var nombreLibro = teclado.nextLine();
        // var json = consumoAPI.obtenerDatos(URL_BASE + nombreLibro.replace(" ",
        // "%20"));
        // //System.out.println(json);
        // DatosGenerales datos = conversor.obtenerDatos(json, DatosGenerales.class);
        // //System.out.println(datos);
        // return datos;

        var json = consumoAPI.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20"));

        DatosGenerales datos = conversor.obtenerDatos(json, DatosGenerales.class);
        libroBuscado = datos.libros().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();

        libroBuscado.ifPresent(val -> System.out.println("Libro encontrado! " + val));
    }

    private void buscarLibroWeb() {

        System.out.println("Escriba el nombre del libro que desea buscar: ");
        var nombreLibro = teclado.nextLine();

        getDatosLibro(nombreLibro);

        if (libroBuscado.isPresent()) {

            Optional<Libro> libro = repository.findByTituloContainsIgnoreCase(libroBuscado.get().titulo());
            if (libro.isPresent()) {
                System.out.println("El título ya existe en la base de datos");
            } else {
                Libro libroNuevo = new Libro();
                libroNuevo.setTitulo(libroBuscado.get().titulo());
                libroNuevo.setIdioma(libroBuscado.get().idioma());
                libroNuevo.setNumeroDeDescargas(libroBuscado.get().numeroDeDescargas());

                
                Autor autor = new Autor();
                autor.setNombre(libroBuscado.get().autor().get(0).nombre());
                autor.setNacimiento(libroBuscado.get().autor().get(0).nacimiento());
                autor.setMuerte(libroBuscado.get().autor().get(0).muerte());

                Optional<Autor> autorExiste = autorRepository.findByNombre(autor.getNombre());

                if (autorExiste.isPresent()) {
                    autor = autorExiste.get();
                } else {
                    autor = autorRepository.save(autor);
                }

                libroNuevo.setAutor(autor);

                repository.save(libroNuevo);
            }
        } else {
            System.out.println("El libro buscado no se encuentra incluido en el index");
        }
    }

    private void mostrarTodosTitulos(){
        libros = repository.findAll();

        libros.forEach(System.out::println);
    }
}