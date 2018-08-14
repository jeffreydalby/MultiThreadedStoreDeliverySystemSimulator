package edu.bu.met.cs665.simulator;

import edu.bu.met.cs665.simulator.namingsystem.FlowerNameGenerator;
import org.junit.Test;

public class FlowerNameGeneratorTest {

    @Test
    public void getName() {
        FlowerNameGenerator flowerNameGenerator = new FlowerNameGenerator();
        System.out.println(flowerNameGenerator.getName());
    }
}