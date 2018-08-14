package edu.bu.met.cs665.simulator.namingsystem;

import java.util.Random;

//Behavior for creating Flower Shop names
public class FlowerNameGenerator implements NameGenerator {
    //Potential names, add pairings here to increase the possibilities
    private String[][] flowerNames = {{"Rainbow", "Bouquet"}, {"Mom's", "Garden"}, {"Secret", "Pedals"},
            {"Happy", "Florist"}, {"May", "Flowers"}};

    /**
     * Get the store name
     *
     * @return - store name
     */
    @Override
    public String getName() {
        Random rnd = new Random();
        String firstName = flowerNames[rnd.nextInt(flowerNames.length)][0];
        String lastName = flowerNames[rnd.nextInt(flowerNames.length)][1];
        return firstName + " " + lastName;
    }
}
