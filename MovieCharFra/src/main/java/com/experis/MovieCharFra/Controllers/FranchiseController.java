package com.experis.MovieCharFra.Controllers;

import com.experis.MovieCharFra.Models.Franchise;
import com.experis.MovieCharFra.Models.Movie;
import com.experis.MovieCharFra.Repositories.FranchiseRepository;
import com.experis.MovieCharFra.Repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/franchises")
public class FranchiseController {
    @Autowired
    private FranchiseRepository franchiseRepository;

    @Autowired
    private MovieRepository movieRepository;


    /**
     * Get all franchises
     *
     * @return - all franchises
     */
    @GetMapping
    public ResponseEntity<List<Franchise>> getAllFranchise() {
        List<Franchise> data = franchiseRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(data, status);
    }

    /**
     * Add franchise to the database
     *
     * @param franchise - franchise
     * @return - the franchise
     */
    @PostMapping
    public ResponseEntity<Franchise> addFranchise(@RequestBody Franchise franchise) {
        Franchise add = franchiseRepository.save(franchise);
        HttpStatus status;
        status = HttpStatus.CREATED;
        return new ResponseEntity<>(add, status);
    }


    /**
     * Get specific franchise
     *
     * @param id -franchise id
     * @return - info about that specific franchise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Franchise> getSpecificFranchise(@PathVariable Long id) {
        HttpStatus status;
        Franchise add = new Franchise();
        if (!franchiseRepository.existsById(id)){
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(add, status);
        }
        add = franchiseRepository.findById(id).get();
        status = HttpStatus.OK;
        return new ResponseEntity<>(add, status);
    }


    /**
     * Update franchise
     *
     * @param id        - franchise id
     * @param franchise - the new franchise
     * @return - newly updated franchise.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Franchise> updateFranchise(@PathVariable("id") Long id, @RequestBody Franchise franchise) {
        Optional<Franchise> franchiseData = franchiseRepository.findById(id);
        if (franchiseData.isPresent()) {
            Franchise _franchise = franchiseData.get();
            _franchise.setName(franchise.getName());
            _franchise.setDescription(franchise.getDescription());

            return new ResponseEntity<>(franchiseRepository.save(_franchise), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * Delete franchise
     *
     * @param id - franchise-id to be deleted
     * @return - franchises without the deleted franchise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFranchise(@PathVariable("id") long id) {
        try {
            franchiseRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update movies in franchises
     *
     * @param id       -franchise id
     * @param moviesId - Movie id
     * @return - the newly updated franchise database
     */
    @PutMapping("{id}/movies")
    public ResponseEntity<Franchise> updateMoviesInFranchise(@PathVariable Long id, @RequestBody List<Long> moviesId) {
        Franchise franchise;
        if (!franchiseRepository.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        franchise = franchiseRepository.findById(id).get();
        List<Movie> movies = new ArrayList<>();
        for (Long movieId : moviesId)
            if (movieRepository.existsById(movieId))
                movies.add(movieRepository.findById(movieId).get());

        franchise.setMovies(movies);
        franchiseRepository.save(franchise);
        return new ResponseEntity<>(franchise, HttpStatus.OK);
    }

    /**
     * Get all movies in franchises
     *
     * @param id - franchise id
     * @return - list of movies in franchises.
     */
    @GetMapping("{id}/getAllMoviesInFranchises")
    public ResponseEntity<List<Movie>> getAllMoviesInFranchises(@PathVariable Long id) {
        Franchise franchise;
        List<Movie> movies = new ArrayList<>();
        HttpStatus status;

        if (franchiseRepository.existsById(id)) {
            status = HttpStatus.OK;
            franchise = franchiseRepository.findById(id).get();
            movies = franchise.getMovies();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(movies, status);
    }


    /**
     * Get all characters in franchises
     *
     * @param id - id
     * @return - list of all characters in franchise.
     */
    @GetMapping("{id}/getAllCharactersInFranchise")
    public ResponseEntity<LinkedHashSet<Character>> getAllCharactersInFranchise(@PathVariable Long id) {
        List<Movie> movies;
        LinkedHashSet characters = new LinkedHashSet<Character>();
        HttpStatus status;
        if (franchiseRepository.existsById(id)) {
            status = HttpStatus.OK;
            movies = franchiseRepository.findById(id).get().getMovies();

            characters = movies.stream()
                    .flatMap(m -> m.getCharacters()
                            .stream()).collect(Collectors.toCollection(LinkedHashSet::new));
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(characters, status);
    }
}
