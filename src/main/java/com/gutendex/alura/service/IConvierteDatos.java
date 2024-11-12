package com.gutendex.alura.service;

public interface IConvierteDatos {
    <T> T obtenerdatos( String json, Class<T> clase );
}
