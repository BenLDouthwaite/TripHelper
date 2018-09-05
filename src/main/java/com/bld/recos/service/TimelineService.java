package com.bld.recos.service;

import com.bld.recos.dto.Timeline;
import com.bld.recos.model.Experience;
import com.bld.recos.model.ExperienceCategory;
import com.bld.recos.model.Journey;
import com.bld.recos.model.JourneyType;
import com.bld.recos.model.TimelineItem;
import com.bld.recos.respository.ExperienceRepository;
import com.bld.recos.respository.JourneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class TimelineService {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private JourneyRepository journeyRepository;

    public Timeline getTimelineBounded(LocalDateTime startTime, LocalDateTime endTime) {

        List<Experience> attractions = experienceRepository.findByCategory(ExperienceCategory.ATTRACTION);
        TimelineItem attraction = getRandomTimelineItem(attractions);

        List<Journey> walkingJourneys = journeyRepository.findByJourneyType(JourneyType.WALKING);
        TimelineItem walkingJourney = getRandomTimelineItem(walkingJourneys);

        List<Experience> restaurants = experienceRepository.findByCategory(ExperienceCategory.RESTAURANT);
        TimelineItem restaurant = getRandomTimelineItem(restaurants);

        List<Journey> waterBusJourneys = journeyRepository.findByJourneyType(JourneyType.WATERBUS);
        TimelineItem waterBusJourney = getRandomTimelineItem(waterBusJourneys);

        List<Experience> landmarks = experienceRepository.findByCategory(ExperienceCategory.LANDMARK);
        TimelineItem landmark = getRandomTimelineItem(landmarks);

        List<Journey> bikeJourneys = journeyRepository.findByJourneyType(JourneyType.BIKE);
        TimelineItem bikeJourney = getRandomTimelineItem(bikeJourneys);

        List<Experience> experiences = experienceRepository.findByCategory(ExperienceCategory.EXPERIENCE);
        TimelineItem experience = getRandomTimelineItem(experiences);

        List<Journey> taxiJourneys = journeyRepository.findByJourneyType(JourneyType.TAXI);
        TimelineItem taxiJourney = getRandomTimelineItem(taxiJourneys);

        Timeline timeline = new Timeline();
        timeline.setTimelineItems(Arrays.asList(attraction, walkingJourney, restaurant, waterBusJourney, landmark, bikeJourney, experience, taxiJourney));
        return timeline;
    }

    public TimelineItem getRandomTimelineItem(List<? extends TimelineItem> experiences) {
        Random random = new Random();
        return experiences.get(random.nextInt(experiences.size()));
    }
}
