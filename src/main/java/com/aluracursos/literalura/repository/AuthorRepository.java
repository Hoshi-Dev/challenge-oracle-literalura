package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNameContains(String name);

    List<Author> findAll();

    @Query("SELECT a FROM Author a WHERE a.birthYear <= :year AND (a.deathYear IS NULL OR a.deathYear >= :year) ORDER BY a.birthYear ASC")
    List<Author> searchAuthorByYear(@Param("year") Integer year);
}
