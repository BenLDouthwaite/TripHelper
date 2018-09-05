package com.bld.recos.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimelineItem {

    private TimelineEvent event;

    private LocalDateTime start;

    private LocalDateTime end;

    private Long startEpoch;

    private Long endEpoch;

}
