package edu.bu.met.cs665.simulator.namingsystem;

import java.util.Random;

//Behavior for creating Dessert Shop names
public class FrozenDinnerNameGenerator implements NameGenerator {
    //Potential names, add pairings here to increase the possibilities
    private String[][] frozenDinnerNames = {{"Easy", "Dinners"}, {"Ready", "Meals"}, {"Home", "Chef"}};

    /**
     * Get the store name
     *
     * @return - store name
     */
    @Override
    public String getName() {
        Random rnd = new Random();
        String firstName = frozenDinnerNames[rnd.nextInt(frozenDinnerNames.length)][0];
        String lastName = frozenDinnerNames[rnd.nextInt(frozenDinnerNames.length)][1];
        return firstName + " " + lastName;
    }
}
