package com.uvt.bachelor.pawfectmatch.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

@Entity
@Table(name = "AWARD")
public class Award {
    
    @Id
    @SequenceGenerator(
            name = "awardGenerator",
            sequenceName = "sq_award_id",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "awardGenerator")
    private Long id;

    @Column
    @Nonnull
    private String name;

    @Column
    @Nonnull
    private Integer ranking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public Award() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public void setName(@Nonnull String name) {
        this.name = name;
    }

    @Nonnull
    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(@Nonnull Integer ranking) {
        this.ranking = ranking;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
