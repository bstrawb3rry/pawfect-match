package com.uvt.bachelor.pawfectmatch.api;

import com.uvt.bachelor.pawfectmatch.service.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/pets/photo")
public class PhotoController {

    private final PhotoService photoService;


    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping(value = "/{photoId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getPhotoByImageId(@PathVariable("photoId") Long id) {
        return ResponseEntity.ok(photoService.getPhotoById(id));
    }

    @PostMapping(value = "/{petId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> uploadPhotoByPetId(@PathVariable("petId") Long id,
                                   @RequestParam("image") MultipartFile photo) throws IOException {
        return ResponseEntity.ok(photoService.uploadPhotoByPetId(id, photo));
    }

}
