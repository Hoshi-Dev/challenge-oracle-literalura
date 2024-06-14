package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true, nullable = false)
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    //cascade = CascadeType.ALL significa que cualquier operación en la entidad Author se aplicará también
    //a las entidades Libro asociadas.
    //orphanRemoval = true asegura que si un libro se elimina de la lista books, se eliminará también de la base de datos
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Book> books = new ArrayList<>();

    public Author() {
    }

    public Author(AuthorData authorData) {
        this.name = authorData.name();
        this.birthYear = Integer.valueOf(authorData.birthYear());
        this.deathYear = Integer.valueOf(authorData.deathYear());
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Author getAuthor(BookData bookData) {
        AuthorData authorData = bookData.author().get(0);
        return new Author(authorData);
    }

    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.setAuthor(null);
    }

    @Override
    public String toString() {
        return name + " (" + birthYear + " - " + deathYear + ")";
    }
}
