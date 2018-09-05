package com.bld.recos.service;

import com.bld.recos.dto.Timeline;
import com.bld.recos.model.*;
import com.bld.recos.respository.ExperienceRepository;
import com.bld.recos.respository.JourneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class TimelineService {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private JourneyRepository journeyRepository;

    private static List<Long> firstTimelineIds = Arrays.asList(15L,3L,1L,49L,11L,43L,9L,40L);
    private static List<Long> secondTimelineIds = Arrays.asList(16L,3L,1L,49L,11L,43L,9L,40L);
    private static List<Long> thirdTimelineIds = Arrays.asList(17L,3L,1L,49L,11L,43L,9L,40L);

    private static Map<Long, Timeline> returnMap;
    static {
        returnMap = new HashMap<>();
    }

    private static Long callCount = 0L;

    private Timeline generateTimeline(List<Long> ids) {

        LocalDateTime startTime = LocalDateTime.of(2018, 9, 4, 10, 0, 0);

        TimelineEvent attraction = experienceRepository.findById(ids.get(0)).get();
        TimelineEvent walking = journeyRepository.findById(ids.get(1)).get();
        TimelineEvent restaurant = experienceRepository.findById(ids.get(2)).get();
        TimelineEvent waterBusJourney = journeyRepository.findById(ids.get(3)).get();
        TimelineEvent landmark = experienceRepository.findById(ids.get(4)).get();
        TimelineEvent bikeJourney = journeyRepository.findById(ids.get(5)).get();
        TimelineEvent experience = experienceRepository.findById(ids.get(6)).get();
        TimelineEvent taxiJourney = journeyRepository.findById(ids.get(7)).get();

        List<TimelineEvent> timelineEvents = Arrays.asList(attraction, walking, restaurant, waterBusJourney, landmark, bikeJourney, experience, taxiJourney);

        Timeline timeline = generateTimeline(startTime, timelineEvents);

        return timeline;
    }

    public Timeline getTimelineBounded(final LocalDateTime startTime, final LocalDateTime endTime) {

        Timeline timeline = returnMap.get(callCount);
        callCount = ++callCount % 3;
        return timeline;
    }

    private Timeline getRandomTimeline(LocalDateTime startTime) {
        List<Experience> attractions = experienceRepository.findByCategory(ExperienceCategory.ATTRACTION);
        TimelineEvent attraction = getRandomTimelineItem(attractions);


        List<Experience> restaurants = experienceRepository.findByCategory(ExperienceCategory.RESTAURANT);
        TimelineEvent restaurant = getRandomTimelineItem(restaurants);

        TimelineEvent attractionToRestaurant = getJourney(attraction, restaurant);

        List<Journey> waterBusJourneys = journeyRepository.findByCategory(JourneyType.WATERBUS);
        TimelineEvent waterBusJourney = getRandomTimelineItem(waterBusJourneys);

        List<Experience> landmarks = experienceRepository.findByCategory(ExperienceCategory.LANDMARK);
        TimelineEvent landmark = getRandomTimelineItem(landmarks);

        List<Journey> bikeJourneys = journeyRepository.findByCategory(JourneyType.BIKE);
        TimelineEvent bikeJourney = getRandomTimelineItem(bikeJourneys);

        List<Experience> experiences = experienceRepository.findByCategory(ExperienceCategory.EXPERIENCE);
        TimelineEvent experience = getRandomTimelineItem(experiences);

        List<Journey> taxiJourneys = journeyRepository.findByCategory(JourneyType.TAXI);
        TimelineEvent taxiJourney = getRandomTimelineItem(taxiJourneys);

        List<TimelineEvent> timelineEvents = Arrays.asList(attraction, attractionToRestaurant, restaurant, waterBusJourney, landmark, bikeJourney, experience, taxiJourney);

        Timeline timeline = generateTimeline(startTime, timelineEvents);

        return timeline;
    }

    private Timeline generateTimeline(LocalDateTime startTime, List<TimelineEvent> timelineEvents) {
        Timeline timeline = new Timeline();

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
        List<Journey> walkingJourneys = journeyRepository.findByCategory(JourneyType.WALKING);

        // TODO How can we guarantee results from here?
        List<Journey> journey = journeyRepository.findByFromIdAndToId(attraction.getId(), restaurant.getId());

        TimelineEvent walkingJourney = getRandomTimelineItem(walkingJourneys);
        return walkingJourney;
    }

    public TimelineEvent getRandomTimelineItem(List<? extends TimelineEvent> experiences) {
        Random random = new Random();
        return experiences.get(random.nextInt(experiences.size()));
    }

    @PostConstruct
    public void initialiseData() {
        Timeline first = generateTimeline(firstTimelineIds);
        Timeline second = generateTimeline(secondTimelineIds);
        Timeline third = generateTimeline(thirdTimelineIds);

        returnMap.put(0L, first);
        returnMap.put(1L, second);
        returnMap.put(2L, third);
    }
}
