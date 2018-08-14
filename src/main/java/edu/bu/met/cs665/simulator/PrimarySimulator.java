package edu.bu.met.cs665.simulator;

import edu.bu.met.cs665.Display.Display;
import edu.bu.met.cs665.customers.Customer;
import edu.bu.met.cs665.customers.SystemCustomers;
import edu.bu.met.cs665.deliverysystem.DeliveryDriver;
import edu.bu.met.cs665.deliverysystem.Dispatch;
import edu.bu.met.cs665.geography.Address;
import edu.bu.met.cs665.simulator.clockticker.ClockTicker;
import edu.bu.met.cs665.simulator.namingsystem.PeopleNameGenerator;
import edu.bu.met.cs665.simulator.namingsystem.StoreNamer;
import edu.bu.met.cs665.stores.Store;
import edu.bu.met.cs665.stores.StoreTypes;
import edu.bu.met.cs665.stores.SystemStores;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class PrimarySimulator {

    //create a scheduled thread pool
    private static ScheduledExecutorService scheduler;
    private static List<ScheduledFuture<?>> driverHandles = new ArrayList<>();

    public void createSimulation(int numStores, int numDrivers, int numCustomers, int numOrders, int milliSecondsBetweenOrders, int numberOfThreads) {
        scheduler = Executors.newScheduledThreadPool(numberOfThreads);

        //we use our own clock so we can do things like create "rush hour events" regardless of system time
        //create and start the clock
        Display.outputWithSeparator("Starting Clock");
        ClockTicker mainClock = ClockTicker.getClockTickerInstance();
        mainClock.startClock();

        //first create the stores
        createSimulationStores(numStores);
        //create the customers
        createCustomers(numCustomers);

        //create the drivers
        createDrivers(numDrivers);
        //launch order simulator in separate thread once we are all set up the orders
        startOrderSimulator(numOrders, milliSecondsBetweenOrders);

    }

    private void startOrderSimulator(int numOrders, int milliSecondsBetweenOrders) {
        OrderSimulator simulatorInstance = OrderSimulator.getInstance();
        simulatorInstance.startSimulation(numOrders,milliSecondsBetweenOrders);
    }

    public static void stopSimulation(){
        Dispatch dispatchInstance = Dispatch.getInstance();
        ClockTicker clockTickerInstance = ClockTicker.getClockTickerInstance();

        Display.outputWithSeparator("No More work to do, shutting down.");
        Display.outputWithSeparator("Sending Drivers Home");
        driverHandles.forEach(driverHandle->driverHandle.cancel(false));

        Display.outputWithSeparator("Stopping dispatcher");
        dispatchInstance.getDispatchThread().interrupt();
        Display.outputWithSeparator("Stopping Clock");
        clockTickerInstance.getClockTickerThread().interrupt();
        Display.outputWithSeparator("Simulation Complete"
                + "\nElapsed Time =" + clockTickerInstance.getSimulatorClock()
                + "\nShutting Down");
        scheduler.shutdown();
    }

    private void createDrivers(int numDrivers) {
        DeliveryDriver deliveryDriver;
        PeopleNameGenerator peopleNameGenerator = new PeopleNameGenerator();
        Random rnd = new Random();
        //30% chance car has cooler most have heaters
        boolean hasCooler = rnd.nextInt(10) < 3;
        boolean hasHeater = rnd.nextInt(10) < 8;

        //we always create one driver with a heater and a cooler to make sure we have that
        deliveryDriver = new DeliveryDriver(peopleNameGenerator.getName(), Address.getRandomGridPoint(),true,true);
        Display.outputWithSeparator("Created new driver: \n" + deliveryDriver.toString());
        //start them up and add to handles list
        driverHandles.add(scheduler.scheduleAtFixedRate(deliveryDriver,500,100,MILLISECONDS));
        //Display.outputWithSeparator("Created new driver: " +deliveryDriver );

        for (int i = 0; i < numDrivers -1 ; i++) {
            deliveryDriver = new DeliveryDriver(peopleNameGenerator.getName(), Address.getRandomGridPoint(),hasHeater,hasCooler);
            //start our driver going and add to handles list
            driverHandles.add(scheduler.scheduleAtFixedRate(deliveryDriver,500,100,MILLISECONDS));
            //get new randoms for next time around
            hasCooler = rnd.nextInt(10) < 3;
            hasHeater = rnd.nextInt(10) < 3;
            Display.outputWithSeparator("Created new driver: \n" + deliveryDriver.toString());
        }
    }

    private void createCustomers(int numCustomers) {
        PeopleNameGenerator peopleNameGenerator = new PeopleNameGenerator();
        SystemCustomers systemCustomers = SystemCustomers.getInstance();
        for (int i = 0; i < numCustomers; i++) {
            systemCustomers.addCustomer(new Customer(Address.getRandomGridPoint(), peopleNameGenerator.getName()));
        }

    }

    private void createSimulationStores(int numStores) {
        SystemStores systemStores = SystemStores.getInstance();
        Store.StoreBuilder builder = new Store.StoreBuilder();
        for (int i = 0; i < numStores; i++) {
            Store newStore = buildStore(builder);
            systemStores.addStore(newStore);
            Display.outputWithSeparator("Created new store: \n" + newStore);
        }

    }

    private static Store buildStore(Store.StoreBuilder builder){
        Random rnd = new Random();
        List<StoreTypes.Type> storeType = getStoreType(rnd.nextInt(10) < 1);
        //give an 90 percent change of not hybrid (only when random int = 0)
        Point storeLocation;
        storeLocation = Address.getRandomGridPoint();
        StoreNamer storeNamer = new StoreNamer();
        return builder.withName(storeNamer.getRandomName(storeType))
                .withLocation(storeLocation)
                .withAddress(Address.getAddress(storeLocation))
                .withStoreClassificationList(storeType)
                .build();
    }

    private static List<StoreTypes.Type> getStoreType(boolean hybridStore) {
        //get our enums as a list of values
        List<StoreTypes.Type> storeTypes = Arrays.asList(StoreTypes.Type.values());
        //shuffle the list.
        Collections.shuffle(storeTypes);
        //return first element if not hybrid otherwise return top two elements
        //note there is NO LOGIC involved so you could end up with a taco/office supply store...which is funny.
        return storeTypes.subList(0, hybridStore ? 2 : 1);

    }


}
