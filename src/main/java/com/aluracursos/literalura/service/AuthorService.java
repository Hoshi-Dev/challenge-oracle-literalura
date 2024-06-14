package com.aluracursos.literalura.service;

import com.aluracursos.literalura.model.Author;
import com.aluracursos.literalura.model.BookData;
import com.aluracursos.literalura.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    //Crea y retorna un libro
    public Author createAuthor(BookData bookData) {
        return new Author().getAuthor(bookData);
    }

    //Guarda un autor en la base de datos
    public void saveAuthor(Author author) {
        try {
            authorRepository.save(author);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Error de integridad al guardar el autor: " + e.getMessage(), e);
        } catch (TransactionSystemException e) {
            throw new RuntimeException("Error de transacción al guardar el autor: " + e.getMessage(), e);
        }
    }

    //Busca un autor en la base de datos
    public Optional<Author> searchAuthorInDB(Author author) {
        var name = author.getName();
        try {
            return authorRepository.findByNameContains(name);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error de acceso a datos: " + e.getMessage(), e);
        }
    }

    //Trae a todos los autores de la base de datos
    public List<Author> getAllAuthorsInDB() {
        try {
            return authorRepository.findAll();
        } catch (DataAccessException e) {
            throw new RuntimeException("Error de acceso a datos: " + e.getMessage(), e);
        }
    }

    //Busca autores en un determinado año
    public List<Author> searchAuthorByYear(int year) {
        try {
            return authorRepository.searchAuthorByYear(year);
        }catch (DataAccessException e) {
            throw new RuntimeException("Error de acceso a datos: " + e.getMessage(), e);
        }
    }

    //Muestra los autores de una lista
    public void showAuthors(List<Author> authors) {
        System.out.println(authors.size() > 1 ? "\nSe encontraron " + authors.size() + " autores:" :
                "\nSe encontró un autor: ");
        IntStream.range(0, authors.size())
                .forEach(i -> System.out.println("Autor n°" + (i + 1) + ": " + authors.get(i)));
    }
}
