package com.uvt.bachelor.pawfectmatch.repository;

import com.uvt.bachelor.pawfectmatch.entity.PetImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetImageRepository extends JpaRepository<PetImage, Long> {
}
