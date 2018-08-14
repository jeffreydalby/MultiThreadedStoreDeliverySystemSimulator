package edu.bu.met.cs665.simulator;

import edu.bu.met.cs665.simulator.namingsystem.ChineseNameGenerator;
import org.junit.Test;

public class ChineseNameGeneratorTest {

    @Test
    public void getName() {
        ChineseNameGenerator chineseNameGenerator = new ChineseNameGenerator();
        System.out.println(chineseNameGenerator.getName());
    }
}