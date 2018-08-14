package edu.bu.met.cs665.simulator.namingsystem;

import java.util.Random;

//Behavior for creating people names
public class PeopleNameGenerator implements NameGenerator {
    //Potential names, add pairings here to increase the possibilities
    private String[][] peopleNames = {{"Bob", "Smith"}, {"Jack", "Parker"}, {"Sophia", "Johnson"},
            {"Jacob", "Williams"}, {"Emma", "Brown"}, {"Mason", "Jones"}, {"Emily", "Miller"}, {"Madison", "Wilson"},
            {"Daniel", "Anderson"}, {"Ella", "Garcia"}, {"Joshua", "Taylor"}, {"Grace", "Hernandez"},
            {"Andrew", "Moore"}, {"Zoey", "Lee"}, {"Ryan", "Perez"}};

    /**
     * Get the store name
     *
     * @return - store name
     */
    @Override
    public String getName() {
        Random rnd = new Random();
        String firstName = peopleNames[rnd.nextInt(peopleNames.length)][0];
        String lastName = peopleNames[rnd.nextInt(peopleNames.length)][1];
        return firstName + " " + lastName;

    }
}
