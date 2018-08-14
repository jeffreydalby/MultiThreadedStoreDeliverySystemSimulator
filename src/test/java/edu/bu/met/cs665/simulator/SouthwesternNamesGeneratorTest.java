package edu.bu.met.cs665.simulator;

import edu.bu.met.cs665.simulator.namingsystem.SouthwesternNamesGenerator;
import org.junit.Test;

public class SouthwesternNamesGeneratorTest {

    @Test
    public void getName() {
        SouthwesternNamesGenerator southwesternNamesGenerator = new SouthwesternNamesGenerator();
        System.out.println(southwesternNamesGenerator.getName());
    }
}