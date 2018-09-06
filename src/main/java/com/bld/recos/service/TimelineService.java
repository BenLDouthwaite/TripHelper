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

        return getRandomTimeline(startTime);
//        Timeline timeline = returnMap.get(callCount);
//        callCount = ++callCount % 3;
//        return timeline;
    }

    private Timeline getRandomTimeline(LocalDateTime startTime) {

        List<Experience> hotels = experienceRepository.findByCategory(ExperienceCategory.HOTEL);
        Experience hotel = getRandomTimelineItem(hotels);

        List<Experience> attractions = experienceRepository.findByCategory(ExperienceCategory.ATTRACTION);
        Experience attraction = getRandomTimelineItem(attractions);

        Journey hotelToAttraction = getJourney(hotel, attraction);

        List<Experience> restaurants = experienceRepository.findByCategory(ExperienceCategory.RESTAURANT);
        Experience restaurant = getRandomTimelineItem(restaurants);

        Journey attractionToRestaurant = getJourney(attraction, restaurant);

        List<Experience> landmarks = experienceRepository.findByCategory(ExperienceCategory.LANDMARK);
        Experience landmark = getRandomTimelineItem(landmarks);

        Journey restaurantToLandmark = getJourney(restaurant, landmark);

        List<Experience> experiences = experienceRepository.findByCategory(ExperienceCategory.EXPERIENCE);
        Experience experience = getRandomTimelineItem(experiences);

        Journey landmarkToExperience = getJourney(landmark, experience);

        List<Experience> airports = experienceRepository.findByCategory(ExperienceCategory.AIRPORT);
        Experience airport = getRandomTimelineItem(airports);

        Journey experienceToAirport = getJourney(experience, airport);

        List<TimelineEvent> timelineEvents = Arrays.asList(hotel, hotelToAttraction, attraction, attractionToRestaurant, restaurant,
                restaurantToLandmark, landmark, landmarkToExperience, experience, experienceToAirport, airport);

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
            item.setStartEpoch(startOfEvent.toEpochSecond(ZoneOffset.UTC) * 1000);

            LocalDateTime endOfEvent = startOfEvent.plusMinutes(timelineEvent.getTimeToSpendInMinutes());

            item.setEnd(endOfEvent);
            item.setEndEpoch(endOfEvent.toEpochSecond(ZoneOffset.UTC) * 1000);

            startOfEvent = endOfEvent;

            timelineItems.add(item);
        }

        timeline.setTimelineItems(timelineItems);
        return timeline;
    }

    private Journey getJourney(Experience from, Experience to) {
        List<Journey> journeys = journeyRepository.findByFromIdAndToId(from.getId(), to.getId());

        if (journeys.isEmpty()) {
            journeys.addAll(fakeJourneys(from, to));
        }

        Journey journey = getRandomTimelineItem(journeys);
        journey.setSuggested(true);
        return journey;
    }

    private List<Journey> fakeJourneys(Experience from, Experience to) {
        List<Journey> result = new ArrayList<>();
        Random random = new Random();

        Journey walkingJourney = new FakeJourney(from, to, JourneyType.WALKING, random);
        if (walkingJourney.getAverageTravelTime() <= 20) {
            result.add(walkingJourney);
        }

        Journey bikeJourney = new FakeJourney(from, to, JourneyType.BIKE, random);
        if (bikeJourney.getAverageTravelTime() <= 25) {
            result.add(bikeJourney);
        }

        result.add(new FakeJourney(from, to, JourneyType.UNDERGROUND, random));
        result.add(new FakeJourney(from, to, JourneyType.BUS, random));
        result.add(new FakeJourney(from, to, JourneyType.TAXI, random));

        if (offerWaterBusBetween(from, to)) {
            result.add(new FakeJourney(from, to, JourneyType.WATERBUS, random));
        }

        return result;
    }

    private boolean offerWaterBusBetween(Experience from, Experience to) {

        String fromName = from.getName();
        String toName = to.getName();
        String[] riverLocations = new String[] { "Tate", "Borough", "Morpeth", "Pauls", "Tower", "Parliament"};

        return Arrays.stream(riverLocations).anyMatch(fromName::contains)
                && Arrays.stream(riverLocations).anyMatch(toName::contains)
                && !from.equals(to);
    }

    public <T extends TimelineEvent> T getRandomTimelineItem(List<T> experiences) {
        Random random = new Random();
        return experiences.get(random.nextInt(experiences.size()));
    }

    @PostConstruct
    public void initialiseData() {
//        Timeline first = generateTimeline(firstTimelineIds);
//        Timeline second = generateTimeline(secondTimelineIds);
//        Timeline third = generateTimeline(thirdTimelineIds);
//
//        returnMap.put(0L, first);
//        returnMap.put(1L, second);
//        returnMap.put(2L, third);
    }

    public List<Journey> getJourneyOptions(Long fromId, Long toId) {
        Experience fromExperience = experienceRepository.findById(fromId).get();
        Experience toExperience = experienceRepository.findById(toId).get();
        List<Journey> journeys = fakeJourneys(fromExperience, toExperience);

        int suggested = new Random().nextInt(journeys.size());
        journeys.get(suggested).setSuggested(true);

        return journeys;
    }
}
