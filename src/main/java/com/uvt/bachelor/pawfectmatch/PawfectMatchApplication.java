package com.uvt.bachelor.pawfectmatch;

import com.uvt.bachelor.pawfectmatch.entity.Pet;
import com.uvt.bachelor.pawfectmatch.entity.PetImage;
import com.uvt.bachelor.pawfectmatch.repository.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.nio.file.Files;

@SpringBootApplication
public class PawfectMatchApplication {

	private final PetOwnerRepository petOwnerRepository;
	private final AddressRepository addressRepository;
	private final AwardRepository awardRepository;
	private final PetRepository petRepository;
	private final MatchRepository matchRepository;
	private final PetImageRepository petImageRepository;

	public PawfectMatchApplication(PetOwnerRepository petOwnerRepository,
                                   AddressRepository addressRepository,
                                   AwardRepository awardRepository,
                                   PetRepository petRepository,
                                   MatchRepository matchRepository, PetImageRepository petImageRepository) {
		this.petOwnerRepository = petOwnerRepository;
		this.addressRepository = addressRepository;
		this.awardRepository = awardRepository;
		this.petRepository = petRepository;
        this.matchRepository = matchRepository;
        this.petImageRepository = petImageRepository;
    }

	public static void main(String[] args) {
		SpringApplication.run(PawfectMatchApplication.class, args);
	}

//	@PostConstruct
	public void constructData() {

//		var owner = petOwnerRepository.findById(2L).get();
//
//		var a = new Pet();
//		a.setAge(1);
//		a.setName("Tara");
//		a.setBreed("Golden Retriever");
//		a.setColor("Gold");
//		a.setDescription("Amazing");
//		a.setGender("female");
//		a.setType("Dog");
//		a.setOwner(owner);
//		petRepository.save(a);
//
//		var b = new Pet();
//		b.setAge(1);
//		b.setName("Bixi");
//		b.setBreed("Golden Retriever");
//		b.setColor("Gold");
//		b.setDescription("Amazing");
//		b.setGender("female");
//		b.setType("Dog");
//		b.setOwner(owner);
//		petRepository.save(b);
//
//		var c = new Pet();
//		c.setAge(1);
//		c.setName("Lora");
//		c.setBreed("Golden Retriever");
//		c.setColor("Gold");
//		c.setDescription("Amazing");
//		c.setGender("female");
//		c.setType("Dog");
//		c.setOwner(owner);
//		petRepository.save(c);
//
//		var d = new Pet();
//		d.setAge(1);
//		d.setName("Gigi");
//		d.setBreed("Golden Retriever");
//		d.setColor("Gold");
//		d.setDescription("Amazing");
//		d.setGender("female");
//		d.setType("Dog");
//		d.setOwner(owner);
//		petRepository.save(d);
//
//		var e = new Pet();
//		e.setAge(1);
//		e.setName("Trixy");
//		e.setBreed("Golden Retriever");
//		e.setColor("Gold");
//		e.setDescription("Amazing");
//		e.setGender("female");
//		e.setType("Dog");
//		e.setOwner(owner);
//		petRepository.save(e);
//
//		var f = new Pet();
//		f.setAge(1);
//		f.setName("Nora");
//		f.setBreed("Golden Retriever");
//		f.setColor("Gold");
//		f.setDescription("Amazing");
//		f.setGender("female");
//		f.setType("Dog");
//		f.setOwner(owner);
//		petRepository.save(f);
//
//		var g = new Pet();
//		g.setAge(1);
//		g.setName("Lulu");
//		g.setBreed("Golden Retriever");
//		g.setColor("Gold");
//		g.setDescription("Amazing");
//		g.setGender("female");
//		g.setType("Dog");
//		g.setOwner(owner);
//		petRepository.save(g);

		var h = petRepository.findById(3L).get();
		var i = petRepository.findById(1L).get();
//		var j = petRepository.findById(8L).get();
//		var k = petRepository.findById(9L).get();
//		var l = petRepository.findById(10L).get();
//		var m = petRepository.findById(11L).get();
//		var n = petRepository.findById(12L).get();

		try {

			addImg("hhhh1.jpeg", h);
			addImg("hhhh2.jpeg", h);
			addImg("iiii1.jpeg", i);
			addImg("iiii2.jpeg", i);
//			addImg("jjjj.jpeg", j);
//			addImg("kkkk.jpeg", k);
//			addImg("llll.jpeg", l);
//			addImg("mmmm.jpeg", m);
//			addImg("nnnn.jpeg", n);

		} catch (Exception ex) {
			System.out.println("Not found");
		}

//		var owner = new PetOwner();
//		owner.setEmail("test@test.com");
//		owner.setFirstName("Michael");
//		owner.setLastName("Jackson");
//		petOwnerRepository.save(owner);
//
//		var address = new Address();
//		address.setCountry("Romania");
//		address.setCounty("Timis");
//		address.setCity("Timisoara");
//		address.setAddress("Unirii");
//		address.setPostalCode(12344);
//		address.setOwner(owner);
//		addressRepository.save(address);
//
//		var award = new Award();
//		award.setName("CACIB");
//		award.setRanking(1);
//		awardRepository.save(award);
//
//		var a = new Pet();
//		a.setAge(1);
//		a.setName("Sierra");
//		a.setAwards(singleton(award));
//		a.setBreed("Golden Retriever");
//		a.setColor("Gold");
//		a.setDescription("Amazing");
//		a.setGender("female");
//		a.setType("Dog");
//		a.setOwner(owner);
//		petRepository.save(a);
//
//		var pancho = new Pet();
//		pancho.setAge(2);
//		pancho.setName("Peanut");
//		pancho.setAwards(singleton(award));
//		pancho.setBreed("Golden Retriever");
//		pancho.setColor("White");
//		pancho.setDescription("Almost Amazing");
//		pancho.setGender("female");
//		pancho.setType("Dog");
//		pancho.setOwner(owner);
//		petRepository.save(pancho);
////
////		var match = new Match();
////		match.setPetInitMatch(pancho);
////		match.setPetResponseMatch(a);
////		matchRepository.save(match);
////
////
//		Optional<Match> match1 = matchRepository.findByPetInitMatchAndPetResponseMatch(a, pancho);
//		Optional<Match> match2 = matchRepository.findByPetInitMatchAndPetResponseMatch(pancho, a);
//
//		if (match1.isPresent() || match2.isPresent()) {
//			if (match1.isPresent()) {
//				Match m1 = match1.get();
//				m1.setFullMatch(true);
//				m1.setMatchDate(OffsetDateTime.now());
//				matchRepository.save(m1);
//			} else {
//				Match m2 = match2.get();
//				m2.setFullMatch(true);
//				m2.setMatchDate(OffsetDateTime.now());
//				matchRepository.save(m2);
//			}
//		} else {
//			var match3 = new Match();
//			match3.setPetInitMatch(pancho);
//			match3.setPetResponseMatch(a);
//		    matchRepository.save(match3);
//		}


	}

	private void addImg(String img, Pet pet) {
		try {
			var resource = new ClassPathResource(
					img).getFile();
			byte[] bytes = Files.readAllBytes(resource.toPath());
			var image = new PetImage();
			image.setPhoto(bytes);
			image.setPet(pet);
			petImageRepository.save(image);
		} catch (Exception ex) {
		System.out.println("Not found");
	}
	}
}
