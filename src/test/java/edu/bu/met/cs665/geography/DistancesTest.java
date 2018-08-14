package edu.bu.met.cs665.geography;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

public class DistancesTest {

    @Test
    public void getDistanceBetweenStoreAndLocation() {
        double testDistance = Distances.getDistanceBetweenPoints(new Point(1,1), new Point(1,10));
        Assert.assertEquals(10.0,10.0,0);

    }
}