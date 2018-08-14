package edu.bu.met.cs665.deliverysystem;

import edu.bu.met.cs665.orders.Order;
import edu.bu.met.cs665.simulator.clockticker.ClockTicker;

//Delivery object consists of an order and driver and things to track if/when it is delievered
public class Delivery {

    //we need to grab the clock so we can updateDelivery tickerstamps
    private ClockTicker clockTickerInstance = ClockTicker.getClockTickerInstance();

    public boolean isPickedUp() {
        return pickedUp;
    }

    void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
        this.pickupTime = clockTickerInstance.getSimulatorClock();
    }

    //for future expansion, we could put these in a database and then be abel to search on which were delivered
    public boolean isDelivered() {
        return delivered;
    }

    void setDelivered(boolean delivered) {
        this.delivered = delivered;
        this.deliveryTime = clockTickerInstance.getSimulatorClock();
    }

    public DeliveryDriver getDeliveryDriver() {
        return driver;
    }

    public void setDeliveryDriver(DeliveryDriver driver) {
        this.driver = driver;
    }

    boolean getRefergerated() {
        return refergerated;
    }

    void setRefergerated(boolean refergerated) {
        this.refergerated = refergerated;
    }

    Order getOrder() {
        return order;
    }

    private Order order;
    private boolean pickedUp;
    private boolean delivered;
    private DeliveryDriver driver;
    private int pickupTime;
    private int orderSubmittedTime; //just using an int to represent time
    private int deliveryTime;
    private boolean refergerated;

    Delivery(DeliveryDriver driver, Order order) {
        this.driver = driver;
        this.order = order;
        this.orderSubmittedTime = clockTickerInstance.getSimulatorClock();
    }

    /**
     * Tells us how long an order sat at the store before being picked up
     *
     * @return - amount of time waited at the store
     */
    int getWaitTime() {
        return this.pickupTime - this.orderSubmittedTime;
    }

    /**
     * tells us how long a deliverey waited in total before being dleivered
     *
     * @return - amount of time it took to get to customers house
     */
    int getDeliveredTime() {
        return this.deliveryTime - this.orderSubmittedTime;
    }


}
