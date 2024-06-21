package com.apiLivros.api.de.livros;

import com.apiLivros.api.de.livros.principal.Principal;
import com.apiLivros.api.de.livros.repository.AutorRepository;
import com.apiLivros.api.de.livros.repository.LivroRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ApiDeLivrosApplication {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public ApiDeLivrosApplication(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiDeLivrosApplication.class, args);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void init() {
        Principal principal = new Principal(livroRepository, autorRepository);
        principal.exibirMenu();
    }
}
    


