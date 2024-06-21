package com.apiLivros.api.de.livros.repository;

import com.apiLivros.api.de.livros.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByTitleContainingIgnoreCase(String titulo);

    List<Livro> findByIdiomasContainingIgnoreCase(String idioma);
}