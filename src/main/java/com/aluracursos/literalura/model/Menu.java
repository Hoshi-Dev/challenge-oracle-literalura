package com.aluracursos.literalura.model;

import com.aluracursos.literalura.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class Menu {
    private final Scanner input = new Scanner(System.in);
    @Autowired
    private MenuService menuService;

    public void showMenu() {
        var option = -1;

        while (option != 0) {
            String menu = """
                       \nIngrese la poción que desea realizar:
                       [1] Buscar libro por título
                       [2] Lista de todos los libros consultados
                       [3] Listado de libros por idioma
                       [4] Top 10 libros más descargados
                       [5] Buscar Autor por nombre
                       [6] Lista de Autores
                       [7] Lista de Autores vivos en determinado año
                       [0] SALIR
                    """;
            System.out.println(menu);

            try {
                option = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese un número válido.");
                input.nextLine();
                continue;
            }

            switch (option) {
                case 1:
                    menuService.searchBookByTitle();
                    break;
                case 2:
                    menuService.allSearchedBooks();
                    break;
                case 3:
                    languageMenu();
                    break;
                case 4:
                    menuService.searchTopTenBooks();
                    break;
                case 5:
                    menuService.searchAuthorByName();
                    break;
                case 6:
                    menuService.searchAllAuthors();
                    break;
                case 7:
                    menuService.searchAuthorByYear();
                    break;
                case 0:
                    System.out.println("\nGRACIAS POR UTILIZAR LITERALURA");
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("\nHa ingresado una opción inválida.");
                    break;
            }
        }
    }

    public void languageMenu() {
        String menu = """
                \nIngrese la opción correspondiente al idioma a buscar:
                [1] Inglés
                [2] Español
                [3] Francés
                """;
        var language = "";
        var option = -1;

        System.out.println(menu);

        try {
            option = input.nextInt();
            input.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Por favor, ingrese un número válido.");
            input.nextLine(); // Limpiar la entrada inválida
            return;
        }

        switch (option) {
            case 1:
                language = "en";
                menuService.booksByLanguage(language);
                break;
            case 2:
                language = "es";
                menuService.booksByLanguage(language);
                break;
            case 3:
                language = "fr";
                menuService.booksByLanguage(language);
                break;
            default:
                System.out.println("Ha ingresado una opción invalida.");
                break;
        }
    }
}
