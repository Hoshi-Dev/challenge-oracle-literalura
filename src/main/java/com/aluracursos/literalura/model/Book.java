package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true, nullable = false)
    private String title;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    //@JoinColumn(name = "autor_id") especifica la columna de unión en la tabla books que contiene
    // la clave externa a la tabla authors
    private Author author;
    private String language;
    private Integer downloads;

    public Book() {
    }

    public Book(BookData bookData) {
        this.title = bookData.title();
        this.author = null;
        this.language = getFirstLanguage(bookData);
        this.downloads = bookData.downloads();
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getFirstLanguage(BookData bookData){
        return bookData.languages().toString();
    }

    @Override
    public String toString() {
        return  "\nTítulo: " + title +
                "\nAutor: " + author +
                "\nLenguaje: " + language +
                "\nDescargas: " + downloads;
    }
}
