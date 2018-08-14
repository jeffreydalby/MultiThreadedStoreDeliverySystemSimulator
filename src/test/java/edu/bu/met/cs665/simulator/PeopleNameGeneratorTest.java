package edu.bu.met.cs665.simulator;

import edu.bu.met.cs665.simulator.namingsystem.PeopleNameGenerator;
import org.junit.Test;

public class PeopleNameGeneratorTest {

    @Test
    public void getName() {
        PeopleNameGenerator peopleNameGenerator = new PeopleNameGenerator();
        System.out.println(peopleNameGenerator.getName());
    }
}