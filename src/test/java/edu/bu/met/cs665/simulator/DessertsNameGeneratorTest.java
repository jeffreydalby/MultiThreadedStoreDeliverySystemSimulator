package edu.bu.met.cs665.simulator;

import edu.bu.met.cs665.simulator.namingsystem.DessertsNameGenerator;
import org.junit.Test;

public class DessertsNameGeneratorTest {

    @Test
    public void getName() {
        DessertsNameGenerator dessertsNameGenerator = new DessertsNameGenerator();
        System.out.println(dessertsNameGenerator.getName());
    }
}