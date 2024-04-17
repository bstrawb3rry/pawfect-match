package com.uvt.bachelor.pawfectmatch.model;

import java.time.OffsetDateTime;

public class MatchDto {

    private Long id;

    private PetDto initiator;

    private PetDto receiver;

    private Boolean fullMatch;

    private OffsetDateTime matchDate;

    public MatchDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetDto getInitiator() {
        return initiator;
    }

    public void setInitiator(PetDto initiator) {
        this.initiator = initiator;
    }

    public PetDto getReceiver() {
        return receiver;
    }

    public void setReceiver(PetDto receiver) {
        this.receiver = receiver;
    }

    public Boolean getFullMatch() {
        return fullMatch;
    }

    public void setFullMatch(Boolean fullMatch) {
        this.fullMatch = fullMatch;
    }

    public OffsetDateTime getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(OffsetDateTime matchDate) {
        this.matchDate = matchDate;
    }
}
