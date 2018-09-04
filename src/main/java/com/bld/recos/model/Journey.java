package com.bld.recos.model;

import javax.persistence.*;

@Entity
@Table(name = "journey")
public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long fromId;

    @Column
    private Long toId;

    @Column
    private JourneyType journeyType;

    @Column
    private Double price;

    @Column
    private Long averageTravelTime;

    public Journey() {
    }
}
