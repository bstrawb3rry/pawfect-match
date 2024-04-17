package com.uvt.bachelor.pawfectmatch.service;

import com.uvt.bachelor.pawfectmatch.Transformer;
import com.uvt.bachelor.pawfectmatch.entity.Match;
import com.uvt.bachelor.pawfectmatch.entity.Pet;
import com.uvt.bachelor.pawfectmatch.exception.PawfectMatchException;
import com.uvt.bachelor.pawfectmatch.model.PetDto;
import com.uvt.bachelor.pawfectmatch.repository.MatchRepository;
import com.uvt.bachelor.pawfectmatch.repository.PetOwnerRepository;
import com.uvt.bachelor.pawfectmatch.repository.PetRepository;
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

    public PetService(PetRepository petRepository,
                      PetOwnerRepository ownerRepository, MatchRepository matchRepository) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
        this.matchRepository = matchRepository;
    }

    public List<PetDto> getPetsByOwner(Long ownerId) {
        return petRepository.findByOwnerId(ownerId).stream().map(Transformer::toDto).collect(Collectors.toList());
    }

    public PetDto addPet(PetDto petDto, Long ownerId) {
        var owner = ownerRepository.findById(ownerId).orElseThrow(() -> new PawfectMatchException(String.format("Owner with id: %d not found.", ownerId)));
        var pet = Transformer.fromDto(petDto);
        pet.setOwner(owner);
        return Transformer.toDto(petRepository.save(pet));
    }

    //todo refactor
    public List<PetDto> getPetsForPossibleMatching(Long id, Long ownerId, Integer age, String color, String awardName, String city) {
        var pet = petRepository.findById(id).orElseThrow(() -> new PawfectMatchException(String.format("Pet with id: %d not found", id)));
        ownerRepository.findById(ownerId).orElseThrow(() -> new PawfectMatchException(String.format("Owner with id: %d not found.", ownerId)));
        List<Pet> matchingPets = petRepository.findMatchingPets(pet.getType(), pet.getBreed(), pet.getGender(), id, ownerId);


        List<Match> partialMatches = matchRepository.findAllByFullMatch(false);
        Set<Pet> initiatedMatches = partialMatches.stream().filter(m -> m.getPetInitMatch() == pet).map(Match::getPetResponseMatch).collect(Collectors.toSet());
        matchingPets.removeAll(initiatedMatches);

        List<Match> fullMatches = matchRepository.findAllByFullMatch(true);
        Set<Pet> fullInitMatches = fullMatches.stream().map(Match::getPetInitMatch).collect(Collectors.toSet());
        Set<Pet> fullResponseMatches = fullMatches.stream().map(Match::getPetResponseMatch).collect(Collectors.toSet());
        matchingPets.removeAll(fullInitMatches);
        matchingPets.removeAll(fullResponseMatches);

        matchingPets = filterPets(age, color, awardName, city, matchingPets);

        return matchingPets.stream().map(Transformer::toDto).collect(Collectors.toList());
    }

    private static List<Pet> filterPets(Integer age, String color, String awardName, String city, List<Pet> matchingPets) {
        if (!isEmpty(age)) {
            matchingPets = matchingPets.stream().filter(p -> p.getAge().equals(age)).collect(Collectors.toList());
        }
        if (!isEmpty(color)) {
            matchingPets = matchingPets.stream().filter(p -> p.getColor().equals(color)).collect(Collectors.toList());
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
        if (!isEmpty(city)) {
            matchingPets = matchingPets.stream().filter(p -> p.getOwner().getAddress().getCity().equals(city)).collect(Collectors.toList());
        }
        return matchingPets;
    }

    public List<PetDto> getPetFullMatches(Long id, Integer age, String color, String awardName, String city) {
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

        matchingPets = filterPets(age, color, awardName, city, matchingPets);

        return matchingPets.stream().map(Transformer::toDto).collect(Collectors.toList());
    }

    @Transactional
    public void deletePet(Long id) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new PawfectMatchException(String.format("Pet with id: %d not found", id)));
        Set<Match> matches = pet.getAllMatches();
        matchRepository.deleteAll(matches);
        petRepository.delete(pet);
    }
}
