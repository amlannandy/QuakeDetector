package com.aknindustries.quakedetector;

public class Earthquake {

    private final String location;
    private final double magnitude;
    private final long timestamp;
    private final String color;
    private final String url;

    Earthquake(String location, double magnitude, long timestamp, String color, String url) {
        this.location = location;
        this.magnitude = magnitude;
        this.timestamp = timestamp;
        this.color = color;
        this.url = url;
    }

    String getLocation() { return this.location; }

    double getMagnitude() { return this.magnitude; }

    long getTimestamp() { return this.timestamp; }

    String getColor() { return this.color; }

    String getUrl() { return this.url; }

}
