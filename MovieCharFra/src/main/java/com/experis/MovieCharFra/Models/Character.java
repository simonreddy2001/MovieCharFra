package com.experis.MovieCharFra.Models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "full_name")
    private String fullName;

    private String alias;

    private String gender;

    private String photo;

    @ManyToMany(mappedBy = "characters")
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
