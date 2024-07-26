package com.uvt.bachelor.pawfectmatch.repository;

import com.uvt.bachelor.pawfectmatch.entity.PetImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PetImageRepository extends JpaRepository<PetImage, Long> {

    Set<PetImage> findPetImageById(Long id);
}
