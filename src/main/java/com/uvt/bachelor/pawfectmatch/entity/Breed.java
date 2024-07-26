package com.uvt.bachelor.pawfectmatch.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

@Entity
@Table(name = "BREED")
public class Breed {

    @Id
    private Long id;

    @Column
    @Nonnull
    private String name;

    @Column
    @Nonnull
    private String type;

    public Breed() {
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
    public String getType() {
        return type;
    }

    public void setType(@Nonnull String type) {
        this.type = type;
    }
}
