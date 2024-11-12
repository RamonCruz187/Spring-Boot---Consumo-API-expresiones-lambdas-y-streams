package com.gutendex.alura.principal;

import com.gutendex.alura.model.DatosLibros;
import com.gutendex.alura.model.ListaDeLibros;
import com.gutendex.alura.service.ConsumoAPI;
import com.gutendex.alura.service.ConvierteDatos;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraMenu() {
        var json = consumoApi.obtenerDatos(URL_BASE);
        System.out.println(json);

        var libros = conversor.obtenerdatos(json, ListaDeLibros.class);
        System.out.println("DATOS");
        libros.libros().stream()
                .limit(5)
                .forEach(System.out::println);

        System.out.println("\nTop 10 libros mas descargados");
        libros.libros().stream()
                .sorted(Comparator.comparing(DatosLibros::descargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);

        System.out.println("\nIngresa el nombre del libro que deseas buscar");
        String busqueda = teclado.nextLine();
        var libroBuscado = consumoApi.obtenerDatos(URL_BASE + "?search=" + busqueda.replace(" ", "%20"));
        var libro = conversor.obtenerdatos(libroBuscado, ListaDeLibros.class);
        libro.libros().stream()
                .findFirst()
                .ifPresent(System.out::println);

        //estadisticas
        DoubleSummaryStatistics est = libros.libros().stream()
                .filter(d -> d.descargas()>0)
                .collect(Collectors.summarizingDouble(DatosLibros::descargas));
        System.out.println("Cantidad de descargas "+est.getAverage());
        System.out.println("Cantidad máxima de descargas "+est.getMax());
        System.out.println("Cantidad mínima de descargas "+est.getMin());
        System.out.println("Cantidad de registros evaluados "+est.getCount());

    }
}
