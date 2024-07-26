package com.uvt.bachelor.pawfectmatch.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @SequenceGenerator(
            name = "userGenerator",
            sequenceName = "sq_users_id",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userGenerator")
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean enabled;

    @OneToOne(mappedBy = "user")
    @Nonnull
    private PetOwner owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Nonnull
    public PetOwner getOwner() {
        return owner;
    }

    public void setOwner(@Nonnull PetOwner owner) {
        this.owner = owner;
    }
}
