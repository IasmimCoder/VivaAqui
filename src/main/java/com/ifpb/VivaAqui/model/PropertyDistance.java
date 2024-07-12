package com.ifpb.VivaAqui.model;

public class PropertyDistance {
    private Property property;
    private double distance;

    public PropertyDistance(Property property, double distance) {
        this.property = property;
        this.distance = distance;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
