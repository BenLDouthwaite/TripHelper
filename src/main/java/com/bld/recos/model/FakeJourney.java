package com.bld.recos.model;

import java.util.Random;

public class FakeJourney extends Journey {

    private Experience from;
    private Experience to;

    public FakeJourney(Experience from, Experience to, JourneyType type, Random random) {
        super();
        setId(random.nextLong());
        setCategory(type);
        this.from = from;
        this.to = to;
        setFromId(from.getId());
        setToId(to.getId());
        generatePriceAndTime(random);
    }

    private void generatePriceAndTime(Random random) {
        double distanceKm = getDistance();

        switch (getCategory()) {
            case BUS:
                setAverageTravelTime((long)(distanceKm * 6 * multiplier(random)));
                setPrice(1.5);
            case BIKE:
                setAverageTravelTime((long)(distanceKm * 6 * multiplier(random)));
                setPrice(2.0);
            case WALKING:
                setAverageTravelTime((long)(distanceKm * 15 * multiplier(random)));
                setPrice(0.0);
            case TAXI:
                setAverageTravelTime((long)(distanceKm * 5 * multiplier(random)));
                setPrice(Math.min(distanceKm * 6, 3.0));
            case UNDERGROUND:
                setAverageTravelTime((long)(distanceKm * 5 * multiplier(random)));
                setPrice(2.4);
            case WATERBUS:
                setAverageTravelTime((long)(distanceKm * 7 * multiplier(random)));
                setPrice(6.5);
            default:
                setAverageTravelTime(99999L);
                setPrice(99999.0);
        }
    }

    private double getDistance() {
        double x = from.getLatitude() - to.getLatitude();
        double y = from.getLongitude() - to.getLongitude();

        return Math.sqrt(x*x + y*y);
    }

    private double multiplier(Random random) {
        return random.nextDouble() * 0.1 + 0.95;
    }

}
