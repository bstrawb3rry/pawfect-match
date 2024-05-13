package com.uvt.bachelor.pawfectmatch.api;

import com.uvt.bachelor.pawfectmatch.service.PhotoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pets/photo/{photoId}")
public class PhotoController {

    private final PhotoService photoService;


    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getPhotoByImageId(@PathVariable("photoId") Long id) {
        return ResponseEntity.ok(photoService.getPhotoById(id));
    }

}
