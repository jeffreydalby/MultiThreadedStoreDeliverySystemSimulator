package edu.bu.met.cs665.deliverysystem;

import edu.bu.met.cs665.Display.Display;
import edu.bu.met.cs665.geography.Distances;
import edu.bu.met.cs665.orders.Order;
import edu.bu.met.cs665.simulator.OrderSimulator;
import edu.bu.met.cs665.simulator.PrimarySimulator;
import edu.bu.met.cs665.simulator.clockticker.ClockTicker;

import java.util.*;

//singleton, only one dispatcher per system
//Automated dispatcher that tracks orders and finds the closest driver
//with the right equipment to deliver it
public class Dispatch implements Subject, Runnable {

    private static Dispatch dispatchInstance;

    //dispatch thread so we can shut it down when done
    public Thread getDispatchThread() {
        return dispatchThread;
    }

    private Thread dispatchThread;

    //Map of all of our drivers
    public static Map<String, DeliveryVehicle> getDriverMap() {
        return driverMap;
    }

    private static volatile Map<String, DeliveryVehicle> driverMap = new HashMap<>();
    private static volatile Deque<Order> orders = new ArrayDeque<>(); //deque to hold the orders
    private static volatile List<Delivery> deliveryList = new ArrayList<>(); //list to hold deliveries as they are created

    static synchronized void addDeliveredOrders(Order deliveredOrder) {
        Dispatch.deliveredOrders.add(deliveredOrder);
    }

    private static volatile List<Order> deliveredOrders = new ArrayList<>();

    private Dispatch() {
        this.dispatchThread = new Thread(this);
        this.dispatchThread.start();
    }

    public static synchronized Dispatch getInstance() {
        if (dispatchInstance == null) dispatchInstance = new Dispatch();
        return dispatchInstance;
    }

    /**
     * Lets drivers register themselves to be able to deliver orders
     *
     * @param identity - name of the driver
     * @param vehicle  - driverObject
     */
    @Override
    public void registerObserver(String identity, DeliveryVehicle vehicle) {
        //if our driver map already has a driver with this name make it unique by adding
        //the current length of the map to it otherwise we'll loose track of a thread
        if (driverMap.containsKey(identity)) {
            identity += " #" + driverMap.size();
            vehicle.setDriverName(identity);
        }

        driverMap.put(identity, vehicle);

    }

    /**
     * Remove driver from the driving map
     *
     * @param driverName - name of driver to remove
     */
    @Override
    public void removeObserver(String driverName) {
        driverMap.remove(driverName);
    }

    //The dispatch system is responsible for sending out notifications to the available driver
    // and doesn't have a need to multicast
    @Override
    public void notifyObserver(DeliveryDriver deliveryDriver, Delivery delivery) {
        deliveryDriver.updateDelivery(delivery);
    }

    /**
     * Requested all drivers post an update to where they are
     */
    @Override
    public void notifyAllObservers() {
        //go through each registered driver and ask for an update
        try {
            driverMap.values().forEach(theDriver -> ((Observer) theDriver).updateStatus());
        } catch (ConcurrentModificationException ex) {
            //just means we are currently still adding drivers
            Display.output("Drivers still being added to system, updates will be available after all drivers have been added.");
        }
    }

    //main automated dispatch system
    @Override
    public void run() {
        //create a loop to run constantly
        while (true) {
            //check if we have been interrupted
            if (Thread.currentThread().isInterrupted()) break;
            //outputWithSeparator an update of the current dispatch summary
            displayDispatchSummary();
            //process the next order, if we are out of orders break out of the loop
            //take a nap to let things processand show the summary
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Display.outputWithSeparator("Killed Dispatch Thread");
                //reset thread so while loop catches it
                Thread.currentThread().isInterrupted();
            }
            //process until we have no more orders
            if (processNextOrder()) break;

            //display an update from all of our drivers
            this.notifyAllObservers();


        }
    }

    private synchronized boolean processNextOrder() {
        DeliveryDriver driver;
        //check for order in the queue and at least one available driver since things could have changed

        if (!orders.isEmpty() && driverMap.values().stream().anyMatch(DeliveryVehicle::isAvailable)) {
            for (int i = 0; i < orders.size(); i++) {

                Order nextOrder = orders.removeFirst();
                //get a driver
                driver = getNearestAvailableDriver(nextOrder);

                if (driver != null) {
                    //let people know we have a traffic event
                    if (isItRushHour() && nextOrder.isNeedsCold())
                        Display.outputWithSeparator("Traffic event, routing refrigerated truck.");
                    //create the delivery and notify the driver
                    createDelivery(driver, nextOrder, isItRushHour() && nextOrder.isNeedsCold());
                }
                //no driver matches so the poor person gets their order sent to the bottom of the queue
                //consider implementing a priority queue so we can bubble things up to the top
                else {
                    orders.addLast(nextOrder);
                }
            }

        } else if (orders.isEmpty()) {
            Display.outputWithSeparator("Order Queue is Empty!");
            //if the order system is done creating orders and all drivers are available
            // and the order queus is empty we can assume we are done
            if (!OrderSimulator.getInstance().isCreatingOrders() && driverMap.values().stream().allMatch(DeliveryVehicle::isAvailable)) {
                PrimarySimulator.stopSimulation();
                return Thread.currentThread().isInterrupted();
            }

        }

        return false;
    }

    private void displayDispatchSummary() {
        Display.outputWithSeparator("Dispatch Update:"
                + "\nNumber of orders waiting to dispatch: " + orders.size()
                + "\nNumber of orders delivered: " + deliveredOrders.size()
                + "\nDrivers Waiting on Assignment: " + driverMap.values().stream().filter(DeliveryVehicle::isAvailable).count()
                + "\nCurrently Rush hour: " + isItRushHour());
    }

    /**
     * Create the Delivery Object
     *
     * @param driver    - driver object who is handling the delivery
     * @param order-    the order to be delivered
     * @param rushHour- was the order created during a traffic event (rush hour)
     */
    private synchronized void createDelivery(DeliveryDriver driver, Order order, boolean rushHour) {
        Delivery newDelivery = new Delivery(driver, order);
        newDelivery.setRefergerated(rushHour);
        deliveryList.add(newDelivery);
        //notify the driver
        notifyObserver(driver, newDelivery);

    }

    /**
     * Find the closest driver that can handle the order
     *
     * @param order - the order we are trying to handle
     * @return -nearest available delivery driver
     */
    private synchronized DeliveryDriver getNearestAvailableDriver(Order order) {
        DeliveryDriver returnDriver = null;
        //initialize to max so we know we'll get the closest
        double closestValue = Double.MAX_VALUE;
        //make sure we have drivers
        if (!driverMap.isEmpty()) {
            for (DeliveryVehicle vehicle : driverMap.values()
            ) {
                //Available vehicles, if it needs heat make sure we have a heater
                if (vehicle.isAvailable()) {
                    //if it needs to be heated and the car has no warmer move on
                    if (order.isNeedsWarm() && !vehicle.hasWarmer()) continue;

                    double storeToCustomerDistance = Distances.getDistanceBetweenPoints(order.getCustomer().getLocation(), order.getStore().getLocation());
                    //if it needs cold we have to check distance and if it is rush hour
                    //if it is rush hour and the vehicle has no cooler move on
                    if (order.isNeedsCold() && isItRushHour()) {
                        if (!vehicle.hasCooler()) continue;
                    }
                    //if the distance is > 2000 units (20 blocks) and no cooler and needs cold move on
                    if (storeToCustomerDistance >= 2000 && !vehicle.hasCooler() && order.isNeedsCold()) continue;
                    //figure out if this is the closest vehicle
                    double thisVehicleDistance = Distances.getDistanceBetweenPoints(order.getStore().getLocation(), vehicle.getCurrentLocation());
                    if (thisVehicleDistance < closestValue) {
                        closestValue = thisVehicleDistance;
                        returnDriver = (DeliveryDriver) vehicle;
                    }
                }
            }
        } else Display.outputWithSeparator("No Drivers Registered");
        if (returnDriver != null)
            Display.outputWithSeparator("Found nearest available driver: " + returnDriver.getDriverName() + "\nDistance to store: " + (int) closestValue + " blocks");
        return returnDriver;
    }

    /**
     * Add the order to the queue
     *
     * @param order - the order to be added
     */
    public void placeOrder(Order order) {
        Display.outputWithSeparator("New Order From Customer: \n"
                + "Order #" + order.getOrderNumber()
                + " \n" + order.toString());
        orders.addLast(order);
    }


    @Override
    public String toString() {
        //return our list of drivers
        StringBuilder returnString = new StringBuilder();
        driverMap.forEach((k, v) -> returnString.append(v.toString() + "\n"));
        return returnString.toString();
    }

    private boolean isItRushHour() {
        return ClockTicker.getClockTickerInstance().getSimulatorClock() > 20 && ClockTicker.getClockTickerInstance().getSimulatorClock() < 80;
    }
}
