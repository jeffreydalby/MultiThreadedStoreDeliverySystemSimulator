package edu.bu.met.cs665.simulator.namingsystem;

import java.util.Random;

//Behavior for creating Pizza Shop names
public class PizzaNameGenerator implements NameGenerator {
    //Potential names, add pairings here to increase the possibilities
    private String[][] pizzaNames = {{"Pizza", "Pizza"}, {"Luigi's", "Parlor"}, {"Hot Stone", "Oven"},
            {"Momma's", "Pizzeria"}, {"Tony's", "Pizza Express"}};

    /**
     * Get the store name
     *
     * @return - store name
     */
    @Override
    public String getName() {
        Random rnd = new Random();
        String firstName = pizzaNames[rnd.nextInt(pizzaNames.length)][0];
        String lastName = pizzaNames[rnd.nextInt(pizzaNames.length)][1];
        return firstName + " " + lastName;
    }
}
