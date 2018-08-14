package edu.bu.met.cs665.simulator;

import edu.bu.met.cs665.Display.Display;
import edu.bu.met.cs665.customers.SystemCustomers;
import edu.bu.met.cs665.deliverysystem.Dispatch;
import edu.bu.met.cs665.stores.SystemStores;
import org.junit.Test;

public class PrimarySimulatorTest {

    @Test
    public void createSimulation() {
        PrimarySimulator testSystem = new PrimarySimulator();
        Dispatch dispatch = Dispatch.getInstance();
        testSystem.createSimulation(10,10,30,100, 5,5);
        SystemStores systemStores = SystemStores.getInstance();
        SystemCustomers systemCustomers = SystemCustomers.getInstance();
        Display.outputWithSeparator(systemStores.toString());
        Display.outputWithSeparator(systemCustomers.toString());
        Display.outputWithSeparator(dispatch.toString());

//        //kill all the driver threads
//        Display.outputWithSeparator("Killing Driver Threads");
//        for (DeliveryVehicle delivieryDriver:dispatch.getDriverMap().values()
//             ) {
//            delivieryDriver.getDriverThread().interrupt(); }
//        //kill dispatch thread
//        Display.outputWithSeparator("Killing dispatch thread");
//        dispatch.getDispatchThread().interrupt();


    }
}