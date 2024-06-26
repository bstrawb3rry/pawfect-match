package com.uvt.bachelor.pawfectmatch.repository;

import com.uvt.bachelor.pawfectmatch.entity.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardRepository extends JpaRepository<Award, Long> {
}
