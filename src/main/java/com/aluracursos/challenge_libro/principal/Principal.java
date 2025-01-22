package com.aluracursos.challenge_libro.principal;

import com.aluracursos.challenge_libro.model.DatosGenerales;
import com.aluracursos.challenge_libro.model.DatosLibro;
import com.aluracursos.challenge_libro.service.ConsumoAPI;
import com.aluracursos.challenge_libro.service.ConvierteDatos;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por título 
                    2 - Buscar episodios
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

            switch (opcion){
                case 1:
                    buscarLibroWeb();
                    break;
            }
        }
    }

    private DatosGenerales getDatosLibro(){
        System.out.println("Escriba el nombre del libro que desea buscar: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20"));
        //System.out.println(json);
        DatosGenerales datos = conversor.obtenerDatos(json, DatosGenerales.class);
        //System.out.println(datos);
        return datos;
    }

    private void buscarLibroWeb() {
        System.out.println("Escriba el nombre del libro que desea buscar: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20"));
        //System.out.println(json);
        DatosGenerales datos = conversor.obtenerDatos(json, DatosGenerales.class);

        Optional<DatosLibro> libroBuscado = datos.libros().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()){
            System.out.println("Libro Encontrado");
            System.out.println(libroBuscado.get());
        }else {
            System.out.println("Libro no encontrado");
        }
    }
}