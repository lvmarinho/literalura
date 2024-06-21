package com.apiLivros.api.de.livros.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private Long anoNascimento;
    private Long anoFalecimento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor(Authors authors) {
        this.nome = authors.nome();
        this.anoNascimento = authors.anoNascimento();
        this.anoFalecimento = authors.anoFalecimento();

    }

    public Autor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(Livro livro) {
        this.livros.add(livro);
        livro.setAutor(this);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Long anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Long getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Long anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nNome: ").append(nome);
        sb.append("\nAnoNascimento: ").append(anoNascimento);
        sb.append("\nAnoFalecimento: ").append(anoFalecimento);
        sb.append("\nLivros: ");
        for (Livro livro : livros) {
            sb.append(livro.getTitle()).append(", ");
        }
        sb.append("\n-------------------------------------\n");
        return sb.toString();

    }

}