package com.bld.recos.service;

import com.bld.recos.dto.Timeline;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TimelineService {

    public Timeline getTimeline() {
        Timeline timeline = new Timeline();

        timeline.setExperiences(new ArrayList<>());

        timeline.setJourneys(new ArrayList<>());

        return timeline;
    }

}
