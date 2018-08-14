package edu.bu.met.cs665.geography;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

public class AddressTest {

    @Test
    public void getAddress() {

        String returnString1 = Address.getAddress(new Point(100,215));
        String returnString2 = Address.getAddress(new Point(135,300));
        Assert.assertEquals("215 100th Avenue", returnString1);
        Assert.assertEquals("135 300th Street", returnString2);
    }

    @Test
    public void getRandomGridPoint() {

        Point testAddress = Address.getRandomGridPoint();
        //either x or y must alway be divisible by 100
        //which is how we know it is a street and not the address
        System.out.println(testAddress);
        Assert.assertTrue(testAddress.x % 100 == 0 || testAddress.y % 100 == 0);
    }
}