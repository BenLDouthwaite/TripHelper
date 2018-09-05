package com.bld.recos.dto;

import com.bld.recos.model.TimelineEvent;
import com.bld.recos.model.TimelineItem;
import lombok.Data;

import java.util.List;

@Data
public class Timeline {

    private List<TimelineItem> timelineItems;

}
