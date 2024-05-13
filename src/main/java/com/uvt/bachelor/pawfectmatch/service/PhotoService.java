package com.uvt.bachelor.pawfectmatch.service;

import com.uvt.bachelor.pawfectmatch.entity.PetImage;
import com.uvt.bachelor.pawfectmatch.repository.PetImageRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PhotoService {

    private final PetImageRepository photoRepository;

    public PhotoService(PetImageRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public byte[] getPhotoById(Long photoId){
        Set<PetImage> photo = photoRepository.findPetImageById(photoId);
        if(photo.isEmpty()){
            throw new IllegalArgumentException("No such photo");
        }
        return photo.stream().findFirst().get().getPhoto();
    }
}
