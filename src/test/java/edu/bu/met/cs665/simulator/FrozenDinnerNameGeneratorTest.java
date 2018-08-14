package edu.bu.met.cs665.simulator;

import edu.bu.met.cs665.simulator.namingsystem.FrozenDinnerNameGenerator;
import org.junit.Test;

public class FrozenDinnerNameGeneratorTest {

    @Test
    public void getName() {
        FrozenDinnerNameGenerator frozenDinnerNameGenerator = new FrozenDinnerNameGenerator();
        System.out.println(frozenDinnerNameGenerator.getName());
    }
}