package com.bld.recos.service;

import com.bld.recos.dto.Timeline;
import com.bld.recos.model.Experience;
import com.bld.recos.model.Journey;
import com.bld.recos.respository.ExperienceRepository;
import com.bld.recos.respository.JourneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimelineService {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private JourneyRepository journeyRepository;

    public Timeline getTimeline() {
        Timeline timeline = new Timeline();

        List<Experience> experienceList = new ArrayList<>();
        List<Journey> journeyList = new ArrayList<>();
        
        timeline.setExperiences(experienceRepository.findAll());
        timeline.setJourneys(journeyRepository.findAll());

        return timeline;
    }

}
