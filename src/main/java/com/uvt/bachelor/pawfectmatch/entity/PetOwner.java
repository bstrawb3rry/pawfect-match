package com.uvt.bachelor.pawfectmatch.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PET_OWNER")
public class PetOwner {

    @Id
    @SequenceGenerator(
            name = "ownerGenerator",
            sequenceName = "sq_owner_id",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ownerGenerator")
    private Long id;

    @Column(name = "first_name")
    @Nonnull
    private String firstName;

    @Column(name = "last_name")
    @Nonnull
    private String lastName;

    @Column
    private String email;

    @OneToMany(mappedBy = "owner")
    private Set<Pet> pets = new HashSet<>();

    @OneToOne(mappedBy = "owner")
    private Address address;

    public PetOwner() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Nonnull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@Nonnull String firstName) {
        this.firstName = firstName;
    }

    @Nonnull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@Nonnull String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}

