package com.experis.MovieCharFra.Controllers;

import com.experis.MovieCharFra.Models.Character;
import com.experis.MovieCharFra.Models.Movie;
import com.experis.MovieCharFra.Repositories.CharacterRepository;
import com.experis.MovieCharFra.Repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CharacterRepository characterRepository;


    /**
     * Get all movies
     *
     * @return all movies
     */
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> data = movieRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(data, status);
    }

    /**
     * Add movie
     *
     * @param movie - movie added
     * @return - movies with the newly added movie
     */
    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie add = movieRepository.save(movie);
        HttpStatus status;
        status = HttpStatus.CREATED;
        // Return a location -> url to get the new resource
        return new ResponseEntity<>(add, status);
    }

    /**
     * Get specific movie
     *
     * @param id - movie-id
     * @return - info about that specific movie
     */

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getSpecificMovie(@PathVariable Long id) {
        HttpStatus status;
        Movie add = new Movie();
        if (!movieRepository.existsById(id)) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(add, status);
        }
        add = movieRepository.findById(id).get();
        status = HttpStatus.OK;
        return new ResponseEntity<>(add, status);
    }

    /**
     * Update movies
     *
     * @param id    - movie-id to be updated
     * @param movie - movie
     * @return - movies with the newly updated movie
     */

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable("id") Long id, @RequestBody Movie movie) {
        Optional<Movie> movieData = movieRepository.findById(id);

        if (movieData.isPresent()) {
            Movie _movie = movieData.get();
            _movie.setTitle(movie.getTitle());
            _movie.setDirector(movie.getDirector());
            _movie.setGenre(movie.getGenre());
            _movie.setReleaseYear(movie.getReleaseYear());
            _movie.setTrailer(movie.getTrailer());
            _movie.setPhoto(movie.getPhoto());

            return new ResponseEntity<>(movieRepository.save(_movie), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    /**
     * Delete movies
     *
     * @param id - movie-id to be deleted
     * @return - movies without the deleted movie
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMovie(@PathVariable("id") long id) {
        try {
            movieRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update characters in movies
     *
     * @param id           - movie-id
     * @param charactarIds - character ids
     * @return - the updated characters in movies.
     */
    @PutMapping("{id}/characters")
    public ResponseEntity<Movie> updateCharactersInMovie(@PathVariable Long id, @RequestBody List<Long> charactarIds) {
        Movie movie;

        if (!movieRepository.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        movie = movieRepository.findById(id).get();
        List<Character> characters = new ArrayList<>();
        for (Long characterId : charactarIds)
            if (characterRepository.existsById(characterId))
                characters.add(characterRepository.findById(characterId).get());

        movie.setCharacters(characters);
        movieRepository.save(movie);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }


    /**
     * Get all characters in movies
     *
     * @param id - id
     * @return - all characters in movies
     */
    @GetMapping("/getAllCharactersInMovie/{id}")
    public ResponseEntity<List<Character>> getAllCharactersInMovies(@PathVariable Long id) {
        List<Character> characters = new ArrayList<>();
        Movie movie;
        HttpStatus status;

        if (movieRepository.existsById(id)) {
            status = HttpStatus.OK;

            movie = movieRepository.findById(id).get();
            characters = movie.getCharacters();

        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(characters, status);
    }
}
