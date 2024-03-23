package com.uvt.bachelor.pawfectmatch.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

@Entity
@Table(name = "ADDRESS")
public class Address {

    @Id
    @SequenceGenerator(
            name = "addressGenerator",
            sequenceName = "sq_address_id",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addressGenerator")
    private Long id;

    @Column
    @Nonnull
    private String country;

    @Column
    @Nonnull
    private String county;

    @Column
    @Nonnull
    private String city;

    @Column
    @Nonnull
    private String address;

    @Column(name = "postal_code")
    @Nonnull
    private Integer postalCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private PetOwner owner;

    public Address() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Nonnull
    public String getCountry() {
        return country;
    }

    public void setCountry(@Nonnull String country) {
        this.country = country;
    }

    @Nonnull
    public String getCounty() {
        return county;
    }

    public void setCounty(@Nonnull String county) {
        this.county = county;
    }

    @Nonnull
    public String getCity() {
        return city;
    }

    public void setCity(@Nonnull String city) {
        this.city = city;
    }

    @Nonnull
    public String getAddress() {
        return address;
    }

    public void setAddress(@Nonnull String address) {
        this.address = address;
    }

    @Nonnull
    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(@Nonnull Integer postalCode) {
        this.postalCode = postalCode;
    }

    public PetOwner getOwner() {
        return owner;
    }

    public void setOwner(PetOwner owner) {
        this.owner = owner;
    }
}
