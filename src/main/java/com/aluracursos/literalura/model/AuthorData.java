package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AuthorData(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") String birthYear,
        @JsonAlias("death_year") String deathYear
) {
}
