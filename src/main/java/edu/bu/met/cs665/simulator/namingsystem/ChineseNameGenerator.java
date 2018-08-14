package edu.bu.met.cs665.simulator.namingsystem;

import java.util.Random;

//Behavior for creating Chinese Restaurant names
public class ChineseNameGenerator implements NameGenerator {
    //potential names
    private String[][] chineseNames = {{"China", "Wok"}, {"PeKing", "Garden"}, {"Szechuan", "Grill"},
            {"Asian", "House"}, {"Mandarin", "Buffet"}};

    /**
     * Get a chinese restaurant name
     *
     * @return -the name
     */
    @Override
    public String getName() {
        Random rnd = new Random();
        String firstName = chineseNames[rnd.nextInt(chineseNames.length)][0];
        String lastName = chineseNames[rnd.nextInt(chineseNames.length)][1];
        return firstName + " " + lastName;
    }
}
