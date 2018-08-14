package edu.bu.met.cs665.simulator;

import edu.bu.met.cs665.simulator.namingsystem.StoreNamer;
import edu.bu.met.cs665.stores.StoreTypes;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class NamerTest {

    @Test
    public void getRandomName() {
        StoreNamer namer = new StoreNamer();
        List<StoreTypes.Type> storeItemTypes = new ArrayList<>();
        storeItemTypes.add(StoreTypes.Type.pizza);
        storeItemTypes.add(StoreTypes.Type.southWestern);
        System.out.println(namer.getRandomName(storeItemTypes));

    }
}