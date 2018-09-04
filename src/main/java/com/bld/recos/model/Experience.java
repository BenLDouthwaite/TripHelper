package com.bld.recos.model;

import javax.persistence.*;

@Entity
@Table(name = "experience")
public class Experience {

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
