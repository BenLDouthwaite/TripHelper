package com.bld.recos.dto;

import com.bld.recos.model.Experience;
import com.bld.recos.model.Journey;

import java.util.List;

public class Timeline {

    private List<Experience> experiences;

    private List<Journey> journeys;

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public List<Journey> getJourneys() {
        return journeys;
    }

    public void setJourneys(List<Journey> journeys) {
        this.journeys = journeys;
    }
}
