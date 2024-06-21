package com.apiLivros.api.de.livros.principal;

import com.apiLivros.api.de.livros.model.Autor;
import com.apiLivros.api.de.livros.model.Dados;
import com.apiLivros.api.de.livros.model.Livro;
import com.apiLivros.api.de.livros.repository.AutorRepository;
import com.apiLivros.api.de.livros.repository.LivroRepository;
import com.apiLivros.api.de.livros.service.ConsumoApi;
import com.apiLivros.api.de.livros.service.ConverteDados;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private final Scanner scanner = new Scanner(System.in);
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConverteDados converteDados = new ConverteDados();
    private final LivroRepository livrosRepository;
    private final AutorRepository autoresRepository;

    public Principal(LivroRepository livrosRepository, AutorRepository autoresRepository) {
        this.livrosRepository = livrosRepository;
        this.autoresRepository = autoresRepository;
    }

    /**
     * 
     */
    public void exibirMenu() {

        var opcao = -1;

        do {
            String menu = """

                    -------Seja bem vindo-------
                   
                    Escolha o número de sua opção:
                    
                    1 - Buscas livro pelo título.
                    2 - Listar livros registrados.
                    3 - Listar Autores registrados.
                    4 - Listar livros em um determinado idioma.
                    5 - Listar Autores vivos em um determinado ano.
                    0 - Sair""";
                   
            System.out.println(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 0:
                    System.out.println("Saindo....");
                    break;
                case 1:
                    try {
                        buscarLivros();
                    } catch (IOException e) {

                        e.printStackTrace();
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarLivrosEmUmDeterminadoIdioma();
                    break;
                case 5:
                    listarAutoresVivosEmUmDeterminadoAno();
                    break;
                default:
                    System.out.println("Opção ínvalida! Digite uma opção valida.");
                    break;
            }

        } while (opcao != 0);

    }

    private void listarAutoresVivosEmUmDeterminadoAno() {
        System.out.print("Digite o ano que deseja pesquisar: ");
        int anoDePesquisa = scanner.nextInt();
        scanner.nextLine();

        List<Autor> autoresEncontrados = autoresRepository.autoresVivosEmUmDeterminadoAno(anoDePesquisa);

        if (autoresEncontrados.isEmpty()) {
            System.out.printf("Nenhum autor vivo no ano %d%n", anoDePesquisa);
        } else {
            System.out.println("Autores vivos no ano " + anoDePesquisa + ":");
            autoresEncontrados.forEach(System.out::println);
        }
    }

    private void listarLivrosEmUmDeterminadoIdioma() {
        System.out.println("Insira o idioma para realizar a busca:");
        System.out.println("en - inglês");
        System.out.println("pt - português");

        String idiomaEscolhido = scanner.nextLine().trim();

        List<Livro> livrosBuscados = livrosRepository.findByIdiomasContainingIgnoreCase(idiomaEscolhido);

        if (!livrosBuscados.isEmpty()) {
            System.out.println("Livros encontrados:");
            livrosBuscados.forEach(System.out::println);
        } else {
            System.out.println("Não existe livro desse idioma no nosso banco de dados!");
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autoresRepository.findAll();
        System.out.println("Autores registrados:");
        autores.forEach(a -> System.out.printf("%s%n", a));
    }

    private void listarLivrosRegistrados() {
        try {
            List<Livro> livros = livrosRepository.findAll();
            if (livros == null || livros.isEmpty()) {
                System.out.println("Sem livros registrados!");
                return;
            }

            livros.stream()
                    .sorted(Comparator.comparing(Livro::getTitle))
                    .forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void buscarLivros() throws IOException, InterruptedException {
        System.out.println("Digite o nome do livro: ");
        String nomeLivro = scanner.nextLine();

        String json = consumoApi.comunicaoApi(nomeLivro);
        Dados dados = converteDados.obterDados(json, Dados.class);
        Livro livro = new Livro(dados);
        Autor autor = new Autor(dados.results().get(0).authors().get(0));

        Optional<Livro> livroBuscado = livrosRepository.findByTitleContainingIgnoreCase(livro.getTitle());
        Optional<Autor> autorBuscado = autoresRepository.findByNomeContaining(autor.getNome());

        if (autorBuscado.isPresent()) {
            var autorEncontrado = autorBuscado.get();
            autorEncontrado.setLivros(livro);
            autoresRepository.save(autorEncontrado);
        } else {
            autor.setLivros(livro);
            autoresRepository.save(autor);
        }

        if (livroBuscado.isPresent()) {
            System.out.println("Livro já cadastrado no Banco");
        } else {
            livrosRepository.save(livro);
        }

        System.out.println(livro);
    }
}
