package com.uvt.bachelor.pawfectmatch.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "PET_IMAGE")
public class PetImage {
    @Id
    @SequenceGenerator(
            name = "imageGenerator",
            sequenceName = "sq_image_id",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "imageGenerator")
    private Long id;
    @Column
    private byte[] photo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public PetImage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
