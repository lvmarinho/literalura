package com.apiLivros.api.de.livros.repository;

import com.apiLivros.api.de.livros.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNomeContaining(String nome);

    @Query("select a from Autor a where a.anoNascimento <= :ano and a.anoFalecimento >= :ano")
    List<Autor> autoresVivosEmUmDeterminadoAno(@Param("ano") int ano);

}
