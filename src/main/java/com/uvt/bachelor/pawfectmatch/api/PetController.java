package com.uvt.bachelor.pawfectmatch.api;

import com.uvt.bachelor.pawfectmatch.model.PetDto;
import com.uvt.bachelor.pawfectmatch.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping(value = "/{id}")
    public void deletePet(@PathVariable("id") Long id) {
        petService.deletePet(id);
    }

    @GetMapping(value = "/{id}/owner/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PetDto>> getPetsForPossibleMatching(@PathVariable("id") Long id, @PathVariable("ownerId") Long ownerId,
                                                                   @RequestParam(required = false) Integer age,
                                                                   @RequestParam(required = false) String color,
                                                                   @RequestParam(required = false) String awardName,
                                                                   @RequestParam(required = false) String city) {
        return ResponseEntity.ok(petService.getPetsForPossibleMatching(id,ownerId, age, color, awardName, city));
    }

    @GetMapping(value = "/{id}/matches", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PetDto>> getPetsMatches(@PathVariable("id") Long id,
                                                       @RequestParam(required = false) Integer age,
                                                       @RequestParam(required = false) String color,
                                                       @RequestParam(required = false) String awardName,
                                                       @RequestParam(required = false) String city) {
        return ResponseEntity.ok(petService.getPetFullMatches(id, age, color, awardName, city));
    }

}
