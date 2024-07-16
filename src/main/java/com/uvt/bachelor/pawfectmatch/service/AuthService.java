package com.uvt.bachelor.pawfectmatch.service;

import com.uvt.bachelor.pawfectmatch.Transformer;
import com.uvt.bachelor.pawfectmatch.entity.Address;
import com.uvt.bachelor.pawfectmatch.entity.PetOwner;
import com.uvt.bachelor.pawfectmatch.entity.User;
import com.uvt.bachelor.pawfectmatch.model.AddressDto;
import com.uvt.bachelor.pawfectmatch.model.PetOwnerDto;
import com.uvt.bachelor.pawfectmatch.model.UserDto;
import com.uvt.bachelor.pawfectmatch.repository.AddressRepository;
import com.uvt.bachelor.pawfectmatch.repository.PetOwnerRepository;
import com.uvt.bachelor.pawfectmatch.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PetOwnerRepository petOwnerRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PetOwnerRepository petOwnerRepository, AddressRepository addressRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.petOwnerRepository = petOwnerRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto signup(UserDto body) {
        User user = new User();
        user.setUsername(body.getUsername());
        user.setPassword(passwordEncoder.encode(body.getPassword()));
        user.setEnabled(true);
        UserDto userDto = Transformer.toDto(userRepository.save(user));

        Address address = new Address();
        AddressDto addressDto = body.getAddress();
        address.setCountry(addressDto.getCountry());
        address.setCounty(addressDto.getCounty());
        address.setCity(addressDto.getCity());
        address.setPostalCode(addressDto.getPostalCode());
        address.setAddress(addressDto.getAddress());
        Address savedAddress = addressRepository.save(address);

        PetOwner petOwner = new PetOwner();
        PetOwnerDto petOwnerDto = body.getPetOwner();
        petOwner.setEmail(petOwnerDto.getEmail());
        petOwner.setFirstName(petOwnerDto.getFirstName());
        petOwner.setLastName(petOwnerDto.getLastName());
        petOwner.setUser(user);
        petOwner.setAddress(address);

        PetOwnerDto ownerDto = Transformer.toDto(petOwnerRepository.save(petOwner));
        userDto.setPetOwner(ownerDto);
        addressDto.setId(savedAddress.getId());
        userDto.setAddress(addressDto);

        //todo
        savedAddress.setOwner(petOwner);
        addressRepository.save(savedAddress);

        return userDto;
    }
}
