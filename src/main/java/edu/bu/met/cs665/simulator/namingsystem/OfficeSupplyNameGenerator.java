package edu.bu.met.cs665.simulator.namingsystem;

import java.util.Random;

//Behavior for creating Office Supply Store names
public class OfficeSupplyNameGenerator implements NameGenerator {
    //Potential names, add pairings here to increase the possibilities
    private String[][] officeSupplyNames = {{"Office", "Supply"}, {"Office", "Depot"}};
    /**
     * Get the store name
     * @return - store name
     */
    @Override
    public String getName() {
        Random rnd = new Random();
        String firstName = officeSupplyNames[rnd.nextInt(officeSupplyNames.length)][0];
        String lastName = officeSupplyNames[rnd.nextInt(officeSupplyNames.length)][1];
        return firstName + " " + lastName;
    }
}
