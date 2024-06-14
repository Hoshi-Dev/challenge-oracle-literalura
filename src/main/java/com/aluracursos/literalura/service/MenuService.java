package com.aluracursos.literalura.service;

import com.aluracursos.literalura.model.Author;
import com.aluracursos.literalura.model.Book;
import com.aluracursos.literalura.model.BookData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private Scanner input = new Scanner(System.in);
    private DataConversor dataConversor = new DataConversor();

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;

    //Busca un libro según su nombre
    public void searchBookByTitle() {
        System.out.println("\nIngrese el título del libro que desea buscar:");
        var searchedBook = input.nextLine();

        var resultData = dataConversor.fetchBookByTitle(searchedBook);

        if (!resultData.results().isEmpty()) {
            BookData bookData = resultData.results().get(0);

            var author = authorService.createAuthor(bookData);
            var book = bookService.createBook(bookData);

            var findedBook = bookService.saveBookWithAuthor(book, author);

            System.out.println(findedBook);
        }else {
            System.out.println("""
                    ------------------------------------------------
                    No se ha encontrado el libro en Gutendex.com
                    ------------------------------------------------""");
        }

    }

    //Listado de todos los libros consultados y guardados en la base de datos
    public void allSearchedBooks() {
        List<Book> searchedBooks = bookService.getAllBook();
        System.out.println("""
                ------------------------------------------------
                El listado de libros consultados es:
                """);

        bookService.showBooks(searchedBooks);
    }

    //Listado de libros por idioma
    public void booksByLanguage(String language) {
        List<Book> booksByLanguage = bookService.getBooksByLanguage(language);

        if (booksByLanguage.isEmpty()) {
            System.out.println("\nNo se han encontrado libros en ese idioma.");
        } else {
            System.out.println("""
                    ------------------------------------------------
                    El listado de libros en el idioma elegido es:
                    """);

            bookService.showBooks(booksByLanguage);
        }
    }

    //Top 10 de libros más descargados
    public void searchTopTenBooks() {
        var searchCondition = "sort";
        List<BookData> results = dataConversor.fetchTopTenBooks(searchCondition);
        List<Book> topTenBooks = new ArrayList<>();

        for (BookData bookData : results) {
            System.out.println(bookData);
            var author = authorService.createAuthor(bookData);
            var book = bookService.createBook(bookData);
            var bookSaved = bookService.saveBookWithAuthor(book, author);
            topTenBooks.add(bookSaved);
        }

        System.out.println("""
                ------------------------------------------------
                Los 10 libros más descargados son:
                """);

        bookService.showBooks(topTenBooks);
    }

    //Busca atores por nombre
    public void searchAuthorByName() {
        System.out.println("\nIngrese el nombre del autor que desea buscar:");
        var authorName = input.nextLine();

        List<Author> authorsInDB = authorService.getAllAuthorsInDB();

        List<Author> filteredAuthors = authorsInDB.stream()
                .filter(a -> a.getName().toLowerCase().contains(authorName))
                .collect(Collectors.toList());

        if (filteredAuthors.isEmpty()) {
            System.out.println("\nNo se ha encontrado el Autor en la base de datos.");
        } else {
            System.out.println("\nAutor encontrado: ");
            filteredAuthors.forEach(System.out::println);
            searchBooksForAuthor(filteredAuthors);
        }
    }

    private void searchBooksForAuthor(List<Author> authors) {
        var message = authors.size() > 1 ? "de los autores?" : "del autor?";

        System.out.println("\n¿Quiere ver los libros " + message + " (Y/N)");
        var response = input.nextLine();

        if (response.equalsIgnoreCase("Y")) {
            authors.stream()
                    .forEach(a -> bookService.showBooks(a.getBooks()));
        }
    }

    //Busca autores vivos en un determinado año
    public void searchAuthorByYear() {
        System.out.println("\nIngrese el año a buscar: ");
        try{
            var year = input.nextInt();
            input.nextLine();

            List<Author> authorsAlive = authorService.searchAuthorByYear(year);

            if (authorsAlive.isEmpty()) {
                System.out.println("\nNo se encontraron autores en la base de datos para el año " + year + ".");
            } else {
                authorService.showAuthors(authorsAlive);
                searchBooksForAuthor(authorsAlive);
            }
        }catch (InputMismatchException e){
            System.out.println("Error: Ingrese un año válido.");
            input.nextLine();
        }
    }

    //Busca todos los autores de la base de datos
    public void searchAllAuthors() {
        List<Author> allAuthors = authorService.getAllAuthorsInDB();

        if (allAuthors.isEmpty()) {
            System.out.println("\nHa ocurrido un problema para recuperar los autores de la base de datos.");
        } else {
            authorService.showAuthors(allAuthors);
        }
    }
}
