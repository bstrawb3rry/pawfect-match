package com.uvt.bachelor.pawfectmatch.service;

import com.uvt.bachelor.pawfectmatch.entity.PetImage;
import com.uvt.bachelor.pawfectmatch.repository.PetImageRepository;
import com.uvt.bachelor.pawfectmatch.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Service
public class PhotoService {

    private final PetImageRepository photoRepository;
    private final PetRepository petRepository;

    public PhotoService(PetImageRepository photoRepository, PetRepository petRepository) {
        this.photoRepository = photoRepository;
        this.petRepository = petRepository;
    }

    public byte[] getPhotoById(Long photoId){
        Set<PetImage> photo = photoRepository.findPetImageById(photoId);
        if(photo.isEmpty()){
            throw new IllegalArgumentException("No such photo");
        }
        return photo.stream().findFirst().get().getPhoto();
    }

    public Long uploadPhotoByPetId(Long id, MultipartFile photo) throws IOException {
        var optionalPet = petRepository.findById(id);
        if(optionalPet.isPresent()) {
            var pet  = optionalPet.get();
            var photoEntity = new PetImage();
            photoEntity.setPhoto(photo.getBytes());
            photoEntity.setPet(pet);
            var savedImage = photoRepository.save(photoEntity);

            var petPhotos = pet.getPhotos();
            petPhotos.add(savedImage);
            pet.setPhotos(petPhotos);
            petRepository.save(pet);
            return savedImage.getId();
        }
        return (long) -1;
    }
}
