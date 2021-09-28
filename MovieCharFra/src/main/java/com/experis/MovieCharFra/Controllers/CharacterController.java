package com.experis.MovieCharFra.Controllers;

import com.experis.MovieCharFra.Models.Character;
import com.experis.MovieCharFra.Repositories.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/characters")
public class CharacterController {
    @Autowired
    private CharacterRepository characterRepository;

    /**
     * Get all characters in the database
     *
     * @return list of characters
     */
    @GetMapping
    public ResponseEntity<List<Character>> getAllCharacters() {
        List<Character> data = characterRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(data, status);
    }


    /**
     * Get a specific character by id
     *
     * @param id - character id
     * @return - Tje character
     */
    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacterById(@PathVariable Long id) {
        Optional<Character> characterData = characterRepository.findById(id);
        return characterData.map(character -> new ResponseEntity<>(character, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * Add a character to the database
     *
     * @param character - the added character
     * @return - the created character
     */
    @PostMapping
    public ResponseEntity<Character> addCharacter(@RequestBody Character character) {
        Character add = characterRepository.save(character);
        HttpStatus status;
        status = HttpStatus.CREATED;
        // Return a location -> url to get the new resource
        return new ResponseEntity<>(add, status);
    }


    /**
     * Update existing character
     *
     * @param character - the new character object
     * @param id        - the character id
     * @return the updated character.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Character> updateCharacter(@RequestBody Character character, @PathVariable Long id) {
        HttpStatus status;
        Character retAdd = new Character();
        if (!Objects.equals(id, character.getId())) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(retAdd, status);
        }
        retAdd = characterRepository.save(character);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(retAdd, status);
    }


    /**
     * Deletes character from the database
     *
     * @param id - character id
     * @return - the database without the deleted character.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCharecter(@PathVariable("id") long id) {
        try {
            characterRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
