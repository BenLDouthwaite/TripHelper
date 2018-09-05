package com.bld.recos.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "journey")
@Data
public class Journey implements TimelineItem{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long fromId;

    @Column
    private Long toId;

    @Enumerated(EnumType.STRING)
    @Column
    private JourneyType journeyType;

    @Column
    private Double price;

    @Column
    private Long averageTravelTime;

    public Journey() {
    }
}
