package edu.bu.met.cs665.simulator.namingsystem;

import java.util.Random;

//Behavior for creating Southwestern Restaurant names
public class SouthwesternNamesGenerator implements NameGenerator {
    //Potential names, add pairings here to increase the possibilities
    private String[][] southWesternNames = {{"Paco's", "Tacos"}, {"Southwestern", "Grill"}, {"Burritos", "Cocina"}};

    /**
     * Get the store name
     *
     * @return - store name
     */
    @Override
    public String getName() {
        Random rnd = new Random();
        String firstName = southWesternNames[rnd.nextInt(southWesternNames.length)][0];
        String lastName = southWesternNames[rnd.nextInt(southWesternNames.length)][1];
        return firstName + " " + lastName;
    }
}
