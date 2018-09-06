package com.bld.recos.controller;

import com.bld.recos.dto.Timeline;
import com.bld.recos.model.Journey;
import com.bld.recos.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TimelineController {

    @Autowired
    private TimelineService timelineService;

    @GetMapping("/timeline/{startTime}/{endTime}")
    public Timeline getTimelineBounded(
            @PathVariable(value = "startTime") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startTime,
            @PathVariable(value = "endTime") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endTime
            ) {
        return timelineService.getTimelineBounded(startTime, endTime);
    }

    @GetMapping("/journey/{fromId}/{toId}")
    public List<Journey> getTimelineBounded(
            @PathVariable(value = "fromId") Long fromId,
            @PathVariable(value = "toId") Long toId
    ) {
        return timelineService.getJourneyOptions(fromId, toId);
    }
}
