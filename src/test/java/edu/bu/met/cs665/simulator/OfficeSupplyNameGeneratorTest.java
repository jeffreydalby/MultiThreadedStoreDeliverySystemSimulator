package edu.bu.met.cs665.simulator;

import edu.bu.met.cs665.simulator.namingsystem.OfficeSupplyNameGenerator;
import org.junit.Test;

public class OfficeSupplyNameGeneratorTest {

    @Test
    public void getName() {
        OfficeSupplyNameGenerator officeSupplyNameGenerator = new OfficeSupplyNameGenerator();
        System.out.println(officeSupplyNameGenerator.getName());
    }
}