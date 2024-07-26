package com.uvt.bachelor.pawfectmatch.repository;

import com.uvt.bachelor.pawfectmatch.entity.Breed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {

    List<Breed> findBreedsByType(@Param("type") String type);
}
