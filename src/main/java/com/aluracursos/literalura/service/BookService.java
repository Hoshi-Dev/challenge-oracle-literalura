package com.aluracursos.literalura.service;

import com.aluracursos.literalura.model.Author;
import com.aluracursos.literalura.model.Book;
import com.aluracursos.literalura.model.BookData;
import com.aluracursos.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorService authorService;

    //Crea un objeto libro
    public Book createBook(BookData bookData) {
        return new Book(bookData);
    }

    //Guarda el libro
    public void saveBook(Book book) {
        try{
            bookRepository.save(book);
        }catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Error de integridad al guardar el libro: " + e.getMessage(), e);
        } catch (TransactionSystemException e) {
            throw new RuntimeException("Error de transacción al guardar el libro: " + e.getMessage(), e);
        }

    }

    //Busca en la base de datos si el libro ya se encuentra registrado
    public boolean searchBookInDB(Book book) {
        try{
            Optional<Book> bookInDB = bookRepository.findByTitle(book.getTitle());
            return bookInDB.isPresent();
        } catch (DataAccessException e) {
            throw new RuntimeException("Error de acceso a datos: " + e.getMessage(), e);
        }

    }

    //Guarda el libro con autor
    public Book saveBookWithAuthor(Book book, Author author) {
        //Se verifica que el libro no esté en la base de datos
        if (searchBookInDB(book)) {
            System.out.println("El libro ya se encuentra en la base de datos.");
            book.setAuthor(author);
        } else {
            //Si el autor no está asignado, se busca el autor en la base datos
            if (book.getAuthor() == null) {
                Optional<Author> authorInDB = authorService.searchAuthorInDB(author);
                Author authorToSave;

                if (authorInDB.isPresent()) {
                    //Si está el authorToSave es igual al que vino de la base de datos
                    authorToSave = authorInDB.get();
                } else {
                    //Si no está, se guarda el autor que llegó como parámetro
                    authorService.saveAuthor(author);
                    authorToSave = author;
                }
                authorToSave.addBook(book);
            }
            saveBook(book);
        }
        return book;
    }

    //Trae todos los libros de la base de datos
    public List<Book> getAllBook() {
        try {
            return bookRepository.findAll();
        } catch (DataAccessException e) {
            throw new RuntimeException("Error de acceso a datos: " + e.getMessage(), e);
        }
    }

    //Trae los libros según un idioma
    public List<Book> getBooksByLanguage(String language) {
        try {
            return bookRepository.findBooksByLanguageContains(language);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error de acceso a datos: " + e.getMessage(), e);
        }
    }

    //Muestra una lista de libros
    public void showBooks(List<Book> books) {
        IntStream.range(0, books.size())
                .forEach(i -> System.out.println("\nLibro n°" + (i + 1) + ":" + books.get(i)));
    }


}
