package com.uvt.bachelor.pawfectmatch.repository;

import com.uvt.bachelor.pawfectmatch.entity.Match;
import com.uvt.bachelor.pawfectmatch.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    public Optional<Match> findByPetInitMatchAndPetResponseMatch(Pet petInitMatch, Pet petResponseMatch);

    @Query("SELECT m FROM Match m WHERE m.fullMatch = :fullMatch")
    List<Match> findAllByFullMatch(boolean fullMatch);

    @Query("SELECT m FROM Match m WHERE (m.petInitMatch = :petInitMatch AND m.petResponseMatch = :petResponseMatch) OR (m.petInitMatch = :petResponseMatch AND m.petResponseMatch = :petInitMatch)")
     Optional<Match> findMatch(@Param("petInitMatch")Pet petInitMatch, @Param("petResponseMatch")Pet petResponseMatch);

    @Query("SELECT m FROM Match m WHERE (m.petInitMatch = :pet OR m.petResponseMatch = :pet) AND m.fullMatch = TRUE ")
    List<Match> findFullMatches(@Param("pet")Pet pet);
}
