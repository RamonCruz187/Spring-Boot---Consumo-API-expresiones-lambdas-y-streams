package com.gutendex.alura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ListaDeLibros(
        @JsonAlias("count")Integer total,
        @JsonAlias("results")List<DatosLibros> libros
) {
}
