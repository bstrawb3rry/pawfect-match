package com.uvt.bachelor.pawfectmatch.repository;

import com.uvt.bachelor.pawfectmatch.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("SELECT p FROM Pet p WHERE p.type = :type AND p.breed = :breed AND p.gender <> :gender AND p.id <> :id AND p.owner.id <> :ownerId")
    List<Pet> findMatchingPets(@Param("type") String type,
                               @Param("breed") String breed,
                               @Param("gender") String gender,
                               @Param("id") Long id,
                               @Param("ownerId") Long ownerId);

    @Query("SELECT p FROM Pet p WHERE p.owner.id = :ownerId")
    List<Pet> findByOwnerId(@Param("ownerId") Long ownerId);



}
