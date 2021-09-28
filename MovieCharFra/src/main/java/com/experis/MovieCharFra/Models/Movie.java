package com.experis.MovieCharFra.Models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Movie {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "title", nullable = false)
        private String title;

        @Column(name = "genre", nullable = false)
        private String genre;

        @Column(name = "release_year")
        private Date releaseYear;

        @Column(name = "director")
        private String director;


        @Column(name = "photo")
        private String photo;

        @Column(name = "trailer")
        private String trailer;

        @ManyToMany
        @JoinTable(
                name = "character_movies",
                joinColumns = {@JoinColumn(name = "movie_id")},
                inverseJoinColumns = {@JoinColumn(name = "character_id")}
        )
        public List<Character> characters;


        @JsonGetter("characters")
        public List<Long> characters() {
                return characters.stream()
                        .map(characterData -> {
                                return characterData.getId();
                        }).collect(Collectors.toList());
        }


        @ManyToOne
        @JoinColumn(name = "franchise_id")
        public Franchise franchise;

        @JsonGetter("franchise")
        public Long franchise() {
                return franchise != null ? franchise.getId() : null;
        }


        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getGenre() {
                return genre;
        }

        public void setGenre(String genre) {
                this.genre = genre;
        }

        public Date getReleaseYear() {
                return releaseYear;
        }

        public void setReleaseYear(Date releaseYear) {
                this.releaseYear = releaseYear;
        }

        public String getDirector() {
                return director;
        }

        public void setDirector(String director) {
                this.director = director;
        }

        public String getPhoto() {
                return photo;
        }

        public void setPhoto(String photo) {
                this.photo = photo;
        }

        public String getTrailer() {
                return trailer;
        }

        public void setTrailer(String trailer) {
                this.trailer = trailer;
        }

        public List<Character> getCharacters() {
                return characters;
        }

        public void setCharacters(List<Character> characters) {
                this.characters = characters;
        }

        public Franchise getFranchise() {
                return franchise;
        }

        public void setFranchise(Franchise franchise) {
                this.franchise = franchise;
        }
}
