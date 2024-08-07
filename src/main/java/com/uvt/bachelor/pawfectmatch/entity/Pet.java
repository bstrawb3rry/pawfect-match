package com.uvt.bachelor.pawfectmatch.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PET")
public class Pet {

    @Id
    @SequenceGenerator(
            name = "petGenerator",
            sequenceName = "sq_pet_id",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "petGenerator")
    private Long id;

    @Column
    @Nonnull
    private String name;

    @Column
    @Nonnull
    private LocalDate birthdate;

    @Column
    @Nonnull
    private String gender;

    @Column
    @Nonnull
    private String type;

    @Column
    @Nonnull
    private String color;

    @Column
    @Nonnull
    private String breed;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private PetOwner owner;

    @OneToMany(mappedBy = "pet")
    private Set<PetImage> photos;

    @OneToMany(mappedBy = "petInitMatch")
    private Set<Match> initiatedMatches;

    @OneToMany(mappedBy = "petResponseMatch")
    private Set<Match> receivedMatches;

    public Pet() {
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
    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(@Nonnull LocalDate age) {
        this.birthdate = age;
    }

    @Nonnull
    public String getGender() {
        return gender;
    }

    public void setGender(@Nonnull String gender) {
        this.gender = gender;
    }

    @Nonnull
    public String getType() {
        return type;
    }

    public void setType(@Nonnull String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Nonnull
    public String getBreed() {
        return breed;
    }

    public void setBreed(@Nonnull String breed) {
        this.breed = breed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PetOwner getOwner() {
        return owner;
    }

    public void setOwner(PetOwner owner) {
        this.owner = owner;
    }

    public Set<Match> getInitiatedMatches() {
        return initiatedMatches;
    }

    public void setInitiatedMatches(Set<Match> initiatedMatches) {
        this.initiatedMatches = initiatedMatches;
    }

    public Set<Match> getReceivedMatches() {
        return receivedMatches;
    }

    public void setReceivedMatches(Set<Match> receivedMatches) {
        this.receivedMatches = receivedMatches;
    }

    public Set<Match> getAllMatches() {
        Set<Match> matches = new HashSet<>();
        matches.addAll(initiatedMatches);
        matches.addAll(receivedMatches);
        return matches;
    }

    public Set<PetImage> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<PetImage> photos) {
        this.photos = photos;
    }
}
