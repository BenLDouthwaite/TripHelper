package com.bld.recos.dto;

import com.bld.recos.model.TimelineItem;

import java.util.List;

public class Timeline {

    public List<TimelineItem> getTimelineItems() {
        return timelineItems;
    }

    public void setTimelineItems(List<TimelineItem> timelineItems) {
        this.timelineItems = timelineItems;
    }

    private List<TimelineItem> timelineItems;
}
