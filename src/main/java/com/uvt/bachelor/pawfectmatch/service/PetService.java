package com.uvt.bachelor.pawfectmatch.service;

import com.uvt.bachelor.pawfectmatch.DistanceUtil;
import com.uvt.bachelor.pawfectmatch.Transformer;
import com.uvt.bachelor.pawfectmatch.entity.*;
import com.uvt.bachelor.pawfectmatch.exception.PawfectMatchException;
import com.uvt.bachelor.pawfectmatch.model.PetDto;
import com.uvt.bachelor.pawfectmatch.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final PetOwnerRepository ownerRepository;
    private final MatchRepository matchRepository;
    private final BreedRepository breedRepository;
    private final ColorRepository colorRepository;
    private final PetImageRepository petImageRepository;
    private final GeolocationService geolocationService;

    public PetService(PetRepository petRepository,
                      PetOwnerRepository ownerRepository, MatchRepository matchRepository,
                      BreedRepository breedRepository, ColorRepository colorRepository, PetImageRepository petImageRepository, GeolocationService geolocationService) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
        this.matchRepository = matchRepository;
        this.breedRepository = breedRepository;
        this.colorRepository = colorRepository;
        this.petImageRepository = petImageRepository;
        this.geolocationService = geolocationService;
    }

    public List<PetDto> getPetsByOwner(Long ownerId) {
        return petRepository.findByOwnerId(ownerId).stream()
                .sorted(Comparator.comparing(Pet::getName))
                .map(Transformer::toDto).collect(Collectors.toList());
    }

    public PetDto addPet(PetDto petDto, Long ownerId) {
        var owner = ownerRepository.findById(ownerId).orElseThrow(() -> new PawfectMatchException(String.format("Owner with id: %d not found.", ownerId)));
        var pet = Transformer.fromDto(petDto);
        pet.setOwner(owner);
        return Transformer.toDto(petRepository.save(pet));
    }

    public PetDto editPet(PetDto petDto) {
        var pet = petRepository.findById(petDto.getId()).orElseThrow(() -> new PawfectMatchException(String.format("Pet with id: %d not found.", petDto.getId())));
        pet.setAge(pet.getAge());
        pet.setColor(pet.getColor());
        pet.setDescription(pet.getDescription());
        return Transformer.toDto(petRepository.save(pet));
    }

    public List<PetDto> getPetsForPossibleMatching(Long id, Long ownerId, Integer startAge, Integer endAge, Integer startKm, Integer endKm, List<String> colors, String awardName) {
        var pet = petRepository.findById(id).orElseThrow(() -> new PawfectMatchException(String.format("Pet with id: %d not found", id)));
        PetOwner petOwner = ownerRepository.findById(ownerId).orElseThrow(() -> new PawfectMatchException(String.format("Owner with id: %d not found.", ownerId)));
        List<Pet> matchingPets = petRepository.findMatchingPets(pet.getType(), pet.getBreed(), pet.getGender(), id, ownerId);

        Address address = petOwner.getAddress();
        double[] ownerCoordinates = geolocationService.getCoordinates(
                address.getPostalCode().toString(),
                address.getCity(),
                address.getCounty(),
                address.getCountry());


        List<Match> partialMatches = matchRepository.findAllByFullMatch(false);
        Set<Pet> initiatedMatches = partialMatches.stream().filter(m -> m.getPetInitMatch() == pet).map(Match::getPetResponseMatch).collect(Collectors.toSet());
        matchingPets.removeAll(initiatedMatches);

        List<Match> fullMatches = matchRepository.findAllByFullMatch(true);
        Set<Pet> myFullInitMatches = fullMatches.stream()
                .filter(m -> m.getPetInitMatch() == pet)
                .map(Match::getPetResponseMatch)
                .collect(Collectors.toSet());
        matchingPets.removeAll(myFullInitMatches);
        Set<Pet> myFullResponseMatches = fullMatches.stream()
                .filter(m -> m.getPetResponseMatch() == pet)
                .map(Match::getPetInitMatch)
                .collect(Collectors.toSet());
        matchingPets.removeAll(myFullResponseMatches);

        matchingPets = filterPets(startAge, endAge, startKm, endKm, colors, awardName, matchingPets, ownerCoordinates);

        return matchingPets.stream().map(Transformer::toDto).collect(Collectors.toList());
    }

    private List<Pet> filterPets(Integer startAge, Integer endAge, Integer startKm, Integer endKm, List<String> colors, String awardName, List<Pet> matchingPets, double[] ownerCoordinates) {
        if (!isEmpty(ownerCoordinates) && (!isEmpty(startKm) || !isEmpty(endKm))) {
            matchingPets = matchingPets.stream()
                    .filter(pet -> {
                        Address petAddress = pet.getOwner().getAddress();
                        double[] petCoordinates = geolocationService.getCoordinates(
                                petAddress.getPostalCode().toString(),
                                petAddress.getCity(),
                                petAddress.getCounty(),
                                petAddress.getCountry()
                        );
                        double distance = DistanceUtil.calculateDistance(
                                ownerCoordinates[0], ownerCoordinates[1],
                                petCoordinates[0], petCoordinates[1]
                        );
                        if (!isEmpty(startKm) && !isEmpty(endKm)) {
                            return startKm <= distance && distance <= endKm;
                        } else {
                            if (!isEmpty(startKm)) {
                                return startKm <= distance;
                            } else {
                                return distance <= endKm;
                            }
                        }
                    })
                    .collect(Collectors.toList());
        }
        if (!isEmpty(startAge)) {
            matchingPets = matchingPets.stream().filter(p -> p.getAge() >= startAge).collect(Collectors.toList());
        }
        if (!isEmpty(endAge)) {
            matchingPets = matchingPets.stream().filter(p -> p.getAge() <= endAge).collect(Collectors.toList());
        }
        if (!isEmpty(colors)) {
            matchingPets = matchingPets.stream().filter(p -> colors.contains(p.getColor())).collect(Collectors.toList());
        }
        if (!isEmpty(awardName)) {
            List<Pet> petsWithAward = new ArrayList<>();
            for (Pet p: matchingPets) {
                boolean hasAward = p.getAwards().stream().anyMatch(a -> a.getName().equals(awardName));
                if (hasAward) {
                    petsWithAward.add(p);
                }
            }
            matchingPets = petsWithAward;
        }
        return matchingPets;
    }

    public List<PetDto> getPetFullMatches(Long id, Integer startAge, Integer endAge, Integer startKm, Integer endKm, List<String> colors, String awardName) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new PawfectMatchException(String.format("Pet with id: %d not found", id)));
        List<Match> fullMatches = matchRepository.findFullMatches(pet).stream()
                .sorted(Comparator.comparing(Match::getMatchDate, OffsetDateTime::compareTo).reversed())
                .toList();
        Set<Pet> initiators = fullMatches.stream().map(Match::getPetInitMatch).filter(petInitMatch -> !Objects.equals(petInitMatch.getId(), id)).collect(Collectors.toSet());
        Set<Pet> receivers = fullMatches.stream().map(Match::getPetResponseMatch).filter(petResponseMatch -> !Objects.equals(petResponseMatch.getId(), id)).collect(Collectors.toSet());
        List<Pet> matchingPets = new ArrayList<>();
        matchingPets.addAll(initiators);
        matchingPets.addAll(receivers);
        matchingPets.remove(pet);

        matchingPets = filterPets(startAge, endAge, startKm, endKm, colors, awardName, matchingPets, null);

        return matchingPets.stream().map(Transformer::toDto).collect(Collectors.toList());
    }

    @Transactional
    public void deletePet(Long id) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new PawfectMatchException(String.format("Pet with id: %d not found", id)));
        Set<Match> matches = pet.getAllMatches();
        matchRepository.deleteAll(matches);
        petImageRepository.deleteAll(pet.getPhotos());
        petRepository.delete(pet);
    }

    public List<String> getBreeds(String type) {
        return breedRepository.findBreedsByType(type).stream().map(Breed::getName).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }

    public List<String> getColors(String type) {
        return colorRepository.findColorsByType(type).stream().map(Color::getColor).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }
}
