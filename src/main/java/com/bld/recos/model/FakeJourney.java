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
                setAverageTravelTime((long)(distanceKm * 6));
                setPrice(1.5);
                break;
            case BIKE:
                setAverageTravelTime((long)(distanceKm * 6));
                setPrice(2.0);
                break;
            case WALKING:
                setAverageTravelTime((long)(distanceKm * 15));
                setPrice(0.0);
                break;
            case TAXI:
                setAverageTravelTime((long)(distanceKm * 5));
                setPrice(roundToTwoDecimalPlaces(Math.max(distanceKm * 6, 3.0)));
                break;
            case UNDERGROUND:
                setAverageTravelTime((long)(distanceKm * 5));
                setPrice(2.4);
                break;
            case WATERBUS:
                setAverageTravelTime((long)(distanceKm * 7));
                setPrice(6.5);
                break;
            default:
                setAverageTravelTime(99999L);
                setPrice(99999.0);
        }
    }

    private double getDistance() {
        double x = from.getLatitude() - to.getLatitude();
        double y = from.getLongitude() - to.getLongitude();

        return Math.sqrt(x*x + y*y) * 70;
    }

    private double roundToTwoDecimalPlaces(double price) {
        return Math.round(price*100.0)/100.0;
    }

}
