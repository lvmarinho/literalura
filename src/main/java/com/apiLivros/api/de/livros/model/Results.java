package com.apiLivros.api.de.livros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Results(@JsonAlias("title") String titulo,
        List<String> languages,
        List<Authors> authors,
        @JsonAlias("download_count") Long downloads) {
}
