package com.uvt.bachelor.pawfectmatch.model;

public class UserDto {

    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    private PetOwnerDto petOwner;
    private AddressDto address;

    public UserDto() {
    }

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public PetOwnerDto getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(PetOwnerDto petOwner) {
        this.petOwner = petOwner;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }
}
