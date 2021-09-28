package com.experis.MovieCharFra.Repositories;

import com.experis.MovieCharFra.Models.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FranchiseRepository extends JpaRepository<Franchise, Long> {
}
