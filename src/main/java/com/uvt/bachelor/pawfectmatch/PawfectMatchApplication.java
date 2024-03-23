package com.uvt.bachelor.pawfectmatch;

import com.uvt.bachelor.pawfectmatch.entity.*;
import com.uvt.bachelor.pawfectmatch.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Optional;

import static java.util.Collections.singleton;

@SpringBootApplication
public class PawfectMatchApplication {

	private final PetOwnerRepository petOwnerRepository;
	private final AddressRepository addressRepository;
	private final AwardRepository awardRepository;
	private final PetRepository petRepository;
	private final MatchRepository matchRepository;

	public PawfectMatchApplication(PetOwnerRepository petOwnerRepository,
                                   AddressRepository addressRepository,
                                   AwardRepository awardRepository,
                                   PetRepository petRepository,
								   MatchRepository matchRepository) {
		this.petOwnerRepository = petOwnerRepository;
		this.addressRepository = addressRepository;
		this.awardRepository = awardRepository;
		this.petRepository = petRepository;
        this.matchRepository = matchRepository;
    }

	public static void main(String[] args) {
		SpringApplication.run(PawfectMatchApplication.class, args);
	}

	@PostConstruct
	public void constructData() {

		var owner = new PetOwner();
		owner.setEmail("test@test.com");
		owner.setFirstName("Jane");
		owner.setLastName("Doe");
		petOwnerRepository.save(owner);

		var address = new Address();
		address.setCountry("Romania");
		address.setCounty("Timis");
		address.setCity("Timisoara");
		address.setAddress("Unirii");
		address.setPostalCode(12344);
		address.setOwner(owner);
		addressRepository.save(address);

		var award = new Award();
		award.setName("CACIB");
		award.setRanking(1);
		awardRepository.save(award);

		var sierra = new Pet();
		sierra.setAge(1);
		sierra.setName("Sierra");
		sierra.setAwards(singleton(award));
		sierra.setBreed("Golden Retriever");
		sierra.setColor("Gold");
		sierra.setDescription("Amazing");
		sierra.setGender("female");
		sierra.setType("Dog");
		sierra.setOwner(owner);
		petRepository.save(sierra);

		var pancho = new Pet();
		pancho.setAge(2);
		pancho.setName("Pancho");
		pancho.setAwards(singleton(award));
		pancho.setBreed("Golden Retriever");
		pancho.setColor("White");
		pancho.setDescription("Almost Amazing");
		pancho.setGender("male");
		pancho.setType("Dog");
		pancho.setOwner(owner);
		petRepository.save(pancho);
//
//		var match = new Match();
//		match.setPetInitMatch(pancho);
//		match.setPetResponseMatch(sierra);
//		matchRepository.save(match);
//
//
		Optional<Match> match1 = matchRepository.findByPetInitMatchAndPetResponseMatch(sierra, pancho);
		Optional<Match> match2 = matchRepository.findByPetInitMatchAndPetResponseMatch(pancho, sierra);

		if (match1.isPresent() || match2.isPresent()) {
			if (match1.isPresent()) {
				Match m1 = match1.get();
				m1.setFullMatch(true);
				m1.setMatchDate(LocalDate.now());
				matchRepository.save(m1);
			} else {
				Match m2 = match2.get();
				m2.setFullMatch(true);
				m2.setMatchDate(LocalDate.now());
				matchRepository.save(m2);
			}
		} else {
			var match3 = new Match();
			match3.setPetInitMatch(pancho);
			match3.setPetResponseMatch(sierra);
		    matchRepository.save(match3);
		}


	}
}
