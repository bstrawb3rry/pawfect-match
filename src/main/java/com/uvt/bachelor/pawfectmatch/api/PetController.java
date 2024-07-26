package com.uvt.bachelor.pawfectmatch.api;

import com.uvt.bachelor.pawfectmatch.model.PetDto;
import com.uvt.bachelor.pawfectmatch.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@RestController
@RequestMapping("/api/pets")
    public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {this.petService = petService;}

    @GetMapping(value = "owner/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PetDto>> getPetsByOwner(@PathVariable("ownerId") Long ownerId) {return ResponseEntity.ok(petService.getPetsByOwner(ownerId));}

    @PostMapping(value = "owner/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PetDto> addPet(@RequestBody PetDto petDto, @PathVariable("ownerId") Long ownerId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.addPet(petDto,ownerId));
    }

    @PutMapping(value = "/edit")
    public ResponseEntity<PetDto> editPet(@RequestBody PetDto petDto) {
        return ResponseEntity.status(HttpStatus.OK).body(petService.editPet(petDto));
    }

    @DeleteMapping(value = "/delete/{petId}")
    public void deletePet(@PathVariable("petId") Long petId) {
        petService.deletePet(petId);
    }

    @GetMapping(value = "/{id}/owner/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PetDto>> getPetsForPossibleMatching(@PathVariable("id") Long id, @PathVariable("ownerId") Long ownerId,
                                                                   @RequestParam(required = false) Integer startAge,
                                                                   @RequestParam(required = false) Integer endAge,
                                                                   @RequestParam(required = false) Integer startKm,
                                                                   @RequestParam(required = false) Integer endKm,
                                                                   @RequestParam(required = false) String colors,
                                                                   @RequestParam(required = false) String awardName) {
        return ResponseEntity.ok(petService.getPetsForPossibleMatching(id,ownerId, startAge, endAge, startKm, endKm, getColorList(colors), awardName));
    }

    @GetMapping(value = "/{id}/matches", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PetDto>> getPetsMatches(@PathVariable("id") Long id,
                                                       @RequestParam(required = false) Integer startAge,
                                                       @RequestParam(required = false) Integer endAge,
                                                       @RequestParam(required = false) Integer startKm,
                                                       @RequestParam(required = false) Integer endKm,
                                                       @RequestParam(required = false) String colors,
                                                       @RequestParam(required = false) String awardName) {
        return ResponseEntity.ok(petService.getPetFullMatches(id, startAge, endAge, startKm, endKm, getColorList(colors), awardName));
    }

    @GetMapping(value = "breeds/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getBreeds(@PathVariable("type") String type) {
        return ResponseEntity.ok(petService.getBreeds(type));
    }

    @GetMapping(value = "colors/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getPet(@PathVariable("type") String type) {
        return ResponseEntity.ok(petService.getColors(type));
    }

    private List<String> getColorList (String colors) {
        List<String> colorList = new ArrayList<>();
        if (!isEmpty(colors)) {
            colorList = Arrays.asList(colors.split(","));
        }
        return colorList;
    }
}
