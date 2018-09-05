package com.bld.recos.service;

import com.bld.recos.dto.Timeline;
import com.bld.recos.model.Experience;
import com.bld.recos.model.ExperienceCategory;
import com.bld.recos.model.Journey;
import com.bld.recos.respository.ExperienceRepository;
import com.bld.recos.respository.JourneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

    public Timeline getTimelineBounded(LocalDateTime startTime, LocalDateTime endTime) {

        List<Experience> attractions = experienceRepository.findByCategory(ExperienceCategory.ATTRACTION);
        Experience attraction = geteRandomExperience(attractions);

        List<Experience> restaurants = experienceRepository.findByCategory(ExperienceCategory.RESTAURANT);
        Experience restaurant = geteRandomExperience(restaurants);

        List<Experience> landmarks = experienceRepository.findByCategory(ExperienceCategory.LANDMARK);
        Experience landmark = geteRandomExperience(landmarks);

        List<Experience> experiences = experienceRepository.findByCategory(ExperienceCategory.EXPERIENCE);
        Experience experience = geteRandomExperience(experiences);

        Timeline timeline = new Timeline();
        timeline.setExperiences(Arrays.asList(attraction, restaurant, landmark, experience));
        timeline.setJourneys(new ArrayList<>());
        return timeline;
    }

    public Experience geteRandomExperience(List<Experience> experiences) {
        Random random = new Random();
        return experiences.get(random.nextInt(experiences.size()));
    }
}
