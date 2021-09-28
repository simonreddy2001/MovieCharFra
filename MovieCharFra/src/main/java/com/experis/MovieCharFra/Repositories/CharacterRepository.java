package com.experis.MovieCharFra.Repositories;

import com.experis.MovieCharFra.Models.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
}
