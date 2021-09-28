package com.experis.MovieCharFra.Models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Franchise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany
    @JoinColumn(name = "franchise_id")
    List<Movie> movies;

    @JsonGetter("movies")
    public List<Long> movies() {
        if (movies != null) {
            return movies.stream()
                    .map(movie -> {
                        return movie.getId();
                    }).collect(Collectors.toList());
        }
        return null;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
