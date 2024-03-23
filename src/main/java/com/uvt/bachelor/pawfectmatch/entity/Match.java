package com.uvt.bachelor.pawfectmatch.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "MATCH")
public class Match {

    @Id
    @SequenceGenerator(
            name = "matchGenerator",
            sequenceName = "pawfect_match.sq_match_id",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "matchGenerator")
    private Long id;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id_init_match")
    private Pet petInitMatch;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id_response_match")
    private Pet petResponseMatch;

    @Column(name = "full_match")
    private Boolean fullMatch;

    @Column(name = "match_date")
    private LocalDate matchDate;

    public Match() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pet getPetInitMatch() {
        return petInitMatch;
    }

    public void setPetInitMatch(Pet petMatch) {
        this.petInitMatch = petMatch;
    }

    public Pet getPetResponseMatch() {
        return petResponseMatch;
    }

    public void setPetResponseMatch(Pet petMatchFor) {
        this.petResponseMatch = petMatchFor;
    }

    public Boolean getFullMatch() {
        return fullMatch;
    }

    public void setFullMatch(Boolean fullMatch) {
        this.fullMatch = fullMatch;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }
}

