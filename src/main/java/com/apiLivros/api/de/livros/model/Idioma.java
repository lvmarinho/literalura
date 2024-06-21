package com.apiLivros.api.de.livros.model;

public record Idioma(String value) {
    public Idioma {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Idioma cannot be null or empty");
        }
    }
}