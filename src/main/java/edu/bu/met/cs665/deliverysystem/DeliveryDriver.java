package edu.bu.met.cs665.deliverysystem;

import edu.bu.met.cs665.Display.Display;
import edu.bu.met.cs665.geography.Address;
import edu.bu.met.cs665.geography.Distances;
import edu.bu.met.cs665.simulator.clockticker.ClockTicker;

import java.awt.*;
//Our driver class, if this was the real world it wouldn't need to be runnable
//since the runnable section is just simulating what a real driver would do

public class DeliveryDriver implements Observer, Runnable, DeliveryVehicle {

    //how many city block should the driver be able to move per clock tick
    private static final int BLOCKS_PER_TICK = 300;

    public Point getCurrentLocation() {
        return currentLocation;
    }

    public boolean hasWarmer() {
        return warmer;
    }

    public boolean hasCooler() {
        return cooler;
    }

    public boolean isAvailable() {
        return available;
    }


    public String getDriverName() {
        return driverName;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    private Point currentLocation; //location of driver
    private boolean warmer; //does the vehicle have a heating compartment/bag to keep food warm
    private boolean cooler; //does the vehicle have a refrigerator to keep food cold
    private boolean available; //are they available for a pickup
    private String driverName; //drivers name
    private Delivery currentDelivery; //what they are delivering
    private int distanceToStore; //how far are they from the store to pick the order up
    private int distanceStoreToCustomer; //how far is the store to the customer
    private int distanceTravelled; //how far have the travelled on this particular trip
    private boolean pickingUp; //are the in the process of picking up from the store

    //create the driver and give him a random starting location
    //set up with a cooler, warmer or both
    public DeliveryDriver(String driverName, Point currentLocation, boolean hasWarmer, boolean hasCooler) {
        this.driverName = driverName;
        this.currentLocation = currentLocation;
        this.warmer = hasWarmer;
        this.cooler = hasCooler;
        this.available = true;
        this.currentDelivery = null;
        registerDriver();
    }

    /**
     * Start up the drivers thread
     */
    private void registerDriver() {
        //register the driver
        Dispatch dispatch = Dispatch.getInstance();
        dispatch.registerObserver(this.driverName, this);
    }

    /**
     * Update from the observable
     *
     * @param delivery - the delivery to be made;
     */
    @Override
    public void updateDelivery(Delivery delivery) {


        this.available = false;
        this.currentDelivery = delivery;
        this.distanceToStore = (int) Distances.getDistanceBetweenPoints(this.currentLocation, delivery.getOrder().getStore().getLocation());
        this.distanceStoreToCustomer = (int) Distances.getDistanceBetweenPoints(delivery.getOrder().getStore().getLocation(), delivery.getOrder().getCustomer().getLocation());
        this.pickingUp = true;

        Display.outputWithSeparator("Order Accepted"
                + "\nDriver: " + this.driverName
                + "\nPicking up Order #" + delivery.getOrder().getOrderNumber()
                + "\nRefrigerated due to traffic: " + this.currentDelivery.getRefergerated()
                + "\nFrom: " + delivery.getOrder().getStore().getName()
                + "\nAt: " + delivery.getOrder().getStore().getAddress()
                + "\nFor: " + delivery.getOrder().getCustomer().getCustomerName()
                + "\nAt: " + delivery.getOrder().getCustomer().getAddress()
                + "\nEstimated Total distance: " + (this.distanceToStore + this.distanceStoreToCustomer));
    }

    @Override
    public void updateStatus() {
        Display.outputWithSeparator("Driver Status: \n" + this.toString());
    }

    /**
     * Simulate a real drivers behavior
     */
    @Override
    public void run() {

            //if the driver isn't available we can assume he has a delivery to make.
            if (!available && this.currentDelivery != null) {
                //if we are picking up decrement the distance to the store
                if (pickingUp) {
                    moveTowardStore();
                }
                //if we aren't available and we aren't picking up, we must be dropping off
                else {
                    moveTowardCustomer();
                }
            }
    }

    /**
     * Move driver toward customer
     */
    private void moveTowardCustomer() {
        distanceStoreToCustomer -= BLOCKS_PER_TICK; //reduce by BLOCKS_PER_TICK
        distanceTravelled += BLOCKS_PER_TICK; //Keep track of how far we have gone
        //if the distance to store <= 0 we are at the customer;
        if (distanceStoreToCustomer <= 0) {
            this.deliverOrder();  //deliver the order and reset for the next one
        }
    }

    /**
     * Move driver towards store
     */
    private void moveTowardStore() {

        distanceToStore -= BLOCKS_PER_TICK; //reduce by BLOCKS_PER_TICK
        distanceTravelled += BLOCKS_PER_TICK; //Keep track of how far we have gone
        //if the distance to store <= 0 we are at the store so pick it up
        if (distanceToStore <= 0) {
            pickupFromStore();
        }
    }

    /**
     * pickup the order from the store
     */
    private void pickupFromStore() {
        //we made it to the store to pickup
        this.currentDelivery.setPickedUp(true);
        Display.outputWithSeparator("Picking up Order #"
                + currentDelivery.getOrder().getOrderNumber() + " at time index: " + ClockTicker.getClockTickerInstance().getSimulatorClock()
                + "\nFrom: " + this.currentDelivery.getOrder().getStore().getName()
                + "\nDelivering to " + this.currentDelivery.getOrder().getCustomer().getAddress()
                + "\nOrder waited for " + this.currentDelivery.getWaitTime() + "time units");

        pickingUp = false;
    }

    /**
     * Make the order as delivered, report it and reset for the next delivery
     */
    private void deliverOrder() {
        ClockTicker clockTickerInstance = ClockTicker.getClockTickerInstance();
        //set our location to the customers house cause we are creepy and just hang there until another order comes in
        this.currentLocation = this.currentDelivery.getOrder().getCustomer().getLocation();
        this.currentDelivery.setDelivered(true);
        this.available = true;
        Display.outputWithSeparator("Delivered Order #" + this.currentDelivery.getOrder().getOrderNumber() + " at time index: " + clockTickerInstance.getSimulatorClock()
                + "\nDriver Name: " + this.driverName
                + "\nRefrigerated: " + this.currentDelivery.getRefergerated()
                + "\nTotal Distance: " + this.distanceTravelled
                + "\nCustomer Name: " + this.currentDelivery.getOrder().getCustomer().getCustomerName()
                + "\nCustomer Address: " + this.currentDelivery.getOrder().getCustomer().getAddress()
                + "\nTotal time: " + this.currentDelivery.getDeliveredTime()
                + "\nFrom: " + this.currentDelivery.getOrder().getStore().getName()
                + "\nOrder Contents: " + this.currentDelivery.getOrder().getOrderItems());
        this.currentDelivery = null;
        //set to -1 to indicate not in use
        this.distanceStoreToCustomer = -1;
        this.distanceToStore = -1;
        this.distanceTravelled = 0;

    }

    @Override
    public String toString() {

        String returnString = ("Name: "
                + this.driverName + "\n"
                + "Last Address: "
                + Address.getAddress(this.currentLocation) + "\n"
                + "Has cooler: " + this.hasCooler()
                + "\n" + "Has warmer: " + this.hasWarmer() + "\n" +
                "Is available: " + this.available);
        if(this.distanceTravelled > 0)
            returnString +="\nDistance travelled: " + this.distanceTravelled + " blocks";
        if (this.distanceStoreToCustomer > 0)
            returnString += "\nCurrent distance to Customer: " + (this.distanceStoreToCustomer + this.distanceToStore) + " blocks.";
        if (this.distanceToStore > 0)
            returnString += "\nDistance to store for pickup: " + this.distanceToStore + " blocks.";
        if(this.currentDelivery != null)
            returnString += "\nCurrently delivering Order #" + this.currentDelivery.getOrder().getOrderNumber()
                    +" \n" + this.currentDelivery.getOrder().toString();

        return returnString;
    }
}
