package com.uvt.bachelor.pawfectmatch.service;

import com.uvt.bachelor.pawfectmatch.Transformer;
import com.uvt.bachelor.pawfectmatch.entity.Match;
import com.uvt.bachelor.pawfectmatch.exception.PawfectMatchException;
import com.uvt.bachelor.pawfectmatch.model.MatchDto;
import com.uvt.bachelor.pawfectmatch.repository.MatchRepository;
import com.uvt.bachelor.pawfectmatch.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final PetRepository petRepository;

    public MatchService(MatchRepository matchRepository, PetRepository petRepository) {
        this.matchRepository = matchRepository;
        this.petRepository = petRepository;
    }

    public MatchDto createMatch(Long initiatorId, Long receiverId) {
        var initiator = petRepository.findById(initiatorId).orElseThrow(() -> new PawfectMatchException(String.format("Pet with id: %d not found", initiatorId)));
        var receiver = petRepository.findById(receiverId).orElseThrow(() -> new PawfectMatchException(String.format("Pet with id: %d not found", receiverId)));

        Match match;
        Optional<Match> possibleMatch = matchRepository.findMatch(initiator, receiver);
        if (possibleMatch.isPresent()) {
            match = possibleMatch.get();
            match.setFullMatch(true);
            match.setMatchDate(OffsetDateTime.now());
        } else {
            match = new Match();
            match.setPetInitMatch(initiator);
            match.setPetResponseMatch(receiver);
        }

        matchRepository.save(match);
        return Transformer.toDto(match);
    }
}
