package com.apiLivros.api.de.livros.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    @Column(unique = true)
    private String title;
    private String nomeAutor;
    @ManyToOne(optional = false)
    @JoinColumn(name = "autor_id", nullable = false, updatable = false)
    private Autor autor;
    private String idiomas;
    private Long downloads;

    public Livro(Dados dados) {
        this.title = dados.results().get(0).titulo();
        this.nomeAutor = dados.results().get(0).authors().get(0).nome();
        this.idiomas = dados.results().get(0).languages().get(0);
        this.downloads = dados.results().get(0).downloads();
        this.autor = new Autor(dados.results().get(0).authors().get(0));

    }

    public Livro() {
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        // autor.setLivros(this);
        this.autor = autor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Long getDownloads() {
        return downloads;
    }

    public void setDownloads(Long downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return "\n----------LIVRO----------" +
                "\nTítulo: " + title +
                "\nAutor: " + nomeAutor +
                "\nIdioma: " + idiomas +
                "\nNúmero de downloads: " + downloads +
                "\n-----------------------";
    }
}
