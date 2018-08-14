package edu.bu.met.cs665.simulator;

import edu.bu.met.cs665.simulator.namingsystem.PizzaNameGenerator;
import org.junit.Test;

public class PizzaNameGeneratorTest {

    @Test
    public void getName() {
        PizzaNameGenerator pizzaNameGenerator = new PizzaNameGenerator();
        System.out.println(pizzaNameGenerator.getName());
    }
}