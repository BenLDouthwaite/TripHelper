package com.bld.recos.service;

import com.bld.recos.dto.Timeline;
import com.bld.recos.model.*;
import com.bld.recos.respository.ExperienceRepository;
import com.bld.recos.respository.JourneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

    public Timeline getTimelineBounded(final LocalDateTime startTime, final LocalDateTime endTime) {

        List<Experience> attractions = experienceRepository.findByCategory(ExperienceCategory.ATTRACTION);
        TimelineEvent attraction = getRandomTimelineItem(attractions);


        List<Experience> restaurants = experienceRepository.findByCategory(ExperienceCategory.RESTAURANT);
        TimelineEvent restaurant = getRandomTimelineItem(restaurants);

        TimelineEvent attractionToRestaurant = getJourney(attraction, restaurant);

        List<Journey> waterBusJourneys = journeyRepository.findByJourneyType(JourneyType.WATERBUS);
        TimelineEvent waterBusJourney = getRandomTimelineItem(waterBusJourneys);

        List<Experience> landmarks = experienceRepository.findByCategory(ExperienceCategory.LANDMARK);
        TimelineEvent landmark = getRandomTimelineItem(landmarks);

        List<Journey> bikeJourneys = journeyRepository.findByJourneyType(JourneyType.BIKE);
        TimelineEvent bikeJourney = getRandomTimelineItem(bikeJourneys);

        List<Experience> experiences = experienceRepository.findByCategory(ExperienceCategory.EXPERIENCE);
        TimelineEvent experience = getRandomTimelineItem(experiences);

        List<Journey> taxiJourneys = journeyRepository.findByJourneyType(JourneyType.TAXI);
        TimelineEvent taxiJourney = getRandomTimelineItem(taxiJourneys);

        Timeline timeline = new Timeline();

        List<TimelineEvent> timelineEvents = Arrays.asList(attraction, attractionToRestaurant, restaurant, waterBusJourney, landmark, bikeJourney, experience, taxiJourney);

        List<TimelineItem> timelineItems = new ArrayList<>();

        LocalDateTime startOfEvent = startTime;

        for (TimelineEvent timelineEvent: timelineEvents) {
            TimelineItem item = new TimelineItem();
            item.setEvent(timelineEvent);
            item.setStart(startOfEvent);
            item.setStartEpoch(startOfEvent.toEpochSecond(ZoneOffset.UTC));

            LocalDateTime endOfEvent = startOfEvent.plusMinutes(timelineEvent.getTimeToSpendInMinutes());

            item.setEnd(endOfEvent);
            item.setEndEpoch(endOfEvent.toEpochSecond(ZoneOffset.UTC));

            startOfEvent = endOfEvent;

            timelineItems.add(item);
        }

        timeline.setTimelineItems(timelineItems);

        return timeline;
    }

    private TimelineEvent getJourney(TimelineEvent attraction, TimelineEvent restaurant) {
        List<Journey> walkingJourneys = journeyRepository.findByJourneyType(JourneyType.WALKING);

        // TODO How can we guarantee results from here?
        List<Journey> journey = journeyRepository.findByFromIdAndToId(attraction.getId(), restaurant.getId());

        TimelineEvent walkingJourney = getRandomTimelineItem(walkingJourneys);
        return walkingJourney;
    }

    public TimelineEvent getRandomTimelineItem(List<? extends TimelineEvent> experiences) {
        Random random = new Random();
        return experiences.get(random.nextInt(experiences.size()));
    }
}
