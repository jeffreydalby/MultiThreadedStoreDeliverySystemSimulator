package edu.bu.met.cs665.simulator.namingsystem;

import java.util.Random;

//Behavior for creating Dessert Shop names
public class DessertsNameGenerator implements NameGenerator {
    //Potential names, add pairings here to increase the possibilities
    private String[][] dessertsNames = {{"The Ice Cream", "Shop"}, {"Ma's Candy", "Store"}, {"Frank's Sugar", "Parlor"}};

    /**
     * Get the store name
     *
     * @return - store name
     */
    @Override
    public String getName() {
        Random rnd = new Random();
        String firstName = dessertsNames[rnd.nextInt(dessertsNames.length)][0];
        String lastName = dessertsNames[rnd.nextInt(dessertsNames.length)][1];
        return firstName + " " + lastName;
    }
}
