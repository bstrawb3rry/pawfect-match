package com.uvt.bachelor.pawfectmatch.api;

import com.uvt.bachelor.pawfectmatch.model.MatchDto;
import com.uvt.bachelor.pawfectmatch.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }


    @PutMapping(value = "/initiator/{initiatorId}/receiver/{receiverId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MatchDto> createMatch(@PathVariable("initiatorId") Long initiatorId, @PathVariable("receiverId") Long receiverId) {
        return ResponseEntity.status(HttpStatus.OK).body(matchService.createMatch(initiatorId, receiverId));
    }
}
