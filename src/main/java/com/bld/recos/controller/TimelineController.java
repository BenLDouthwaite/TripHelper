package com.bld.recos.controller;

import com.bld.recos.dto.Timeline;
import com.bld.recos.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimelineController {

    @Autowired
    private TimelineService timelineService;

    @GetMapping("/timeline")
    public Timeline getTimeline() {
        return timelineService.getTimeline();
    }
}
