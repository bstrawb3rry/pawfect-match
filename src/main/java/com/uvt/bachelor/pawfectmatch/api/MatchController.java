package com.uvt.bachelor.pawfectmatch.api;

import com.uvt.bachelor.pawfectmatch.service.MatchService;
import org.springframework.http.MediaType;
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
    public boolean createMatch(@PathVariable("initiatorId") Long initiatorId, @PathVariable("receiverId") Long receiverId) {
        return matchService.createMatch(initiatorId, receiverId);
    }
}
