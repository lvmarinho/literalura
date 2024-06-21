
package com.apiLivros.api.de.livros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Dados(
        @JsonAlias("results") List<Results> results) {
    // Add a constructor to handle the case where results is null
    public Dados {
        results = results == null ? List.of() : results;
    }
}