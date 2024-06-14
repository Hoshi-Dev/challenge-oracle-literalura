package com.aluracursos.literalura;

import com.aluracursos.literalura.model.Menu;
import com.aluracursos.literalura.repository.AuthorRepository;
import com.aluracursos.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

    @Autowired
    private Menu menu;

    public static void main(String[] args) {
        SpringApplication. run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("""
                ------------------------------------------------
                BIENVENIDO A LITERALURA
                ------------------------------------------------
                """);

        menu.showMenu();
    }
}