package com.uvt.bachelor.pawfectmatch.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "COLOR")
public class Color {

    @Id
    private Long id;

    @Column
    @Nonnull
    private String color;

    @Column
    @Nonnull
    private String type;

    public Color() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Nonnull
    public String getColor() {
        return color;
    }

    public void setColor(@Nonnull String name) {
        this.color = name;
    }

    @Nonnull
    public String getType() {
        return type;
    }

    public void setType(@Nonnull String type) {
        this.type = type;
    }
}
