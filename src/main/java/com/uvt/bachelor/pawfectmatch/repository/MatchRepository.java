package com.uvt.bachelor.pawfectmatch.repository;

import com.uvt.bachelor.pawfectmatch.entity.Match;
import com.uvt.bachelor.pawfectmatch.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    public Optional<Match> findByPetInitMatchAndPetResponseMatch(Pet petInitMatch, Pet petResponseMatch);
}
