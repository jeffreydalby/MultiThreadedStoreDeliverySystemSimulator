package edu.bu.met.cs665;

import edu.bu.met.cs665.Display.MenuSystem;
import edu.bu.met.cs665.simulator.PrimarySimulator;

public class Main {

    /**
     * A main method to run examples.
     *
     * @param args not used
     */
    public static void main(String[] args) {


        //the simulation is started by PrimarySimulator and allows for any of the parameters to be changed
        //create and start up the simulator
        MenuSystem menu = new MenuSystem();
        menu.displayWelcome();
        PrimarySimulator primarySimulator = new PrimarySimulator();
        primarySimulator.createSimulation(menu.requestNumStores(),
                menu.requestNumDrivers(),
                menu.requestNumCustomers(),
                menu.requestNumOrders(),
                menu.requestTimeBetweenOrders(),
                menu.requestNumThreads());

    }

}

