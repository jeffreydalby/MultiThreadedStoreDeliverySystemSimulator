package edu.bu.met.cs665.deliverysystem;

import java.awt.*;

//Delivery Drivers all have vehicles which have features, location and availability
public interface DeliveryVehicle {
    Point getCurrentLocation();
    boolean hasWarmer();
    boolean hasCooler();
    boolean isAvailable();
    void setDriverName(String driverName);
    String getDriverName();
}
