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
@Table(name = "experience")
@Data
public class Experience implements TimelineItem{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column
    private ExperienceCategory category;

    @Column
    private Double price;

    @Column
    private Long averageTimeSpent;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    public Experience() {

    }
}
