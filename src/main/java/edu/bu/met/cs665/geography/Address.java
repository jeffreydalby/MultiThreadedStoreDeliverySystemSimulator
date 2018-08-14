package edu.bu.met.cs665.geography;

import java.awt.*;
import java.util.Random;

//Helper class to allow us to get a point on a grid and turn it into a street address
public class Address {

    /**
     * Find us a point on a 10,000 x 10,000 grid
     *
     * @return - point on the grid
     */
    public static Point getRandomGridPoint() {
        //north/south address vs east/west
        boolean isNorthSouth = false;
        Random rnd = new Random();
        //get two numbers one is a multiple of 100 and is the street, one is completely random
        int street = rnd.nextInt(101) * 100;
        int address = rnd.nextInt(10001);

        //50/50 chance of a north south
        if (rnd.nextInt(10) < 5) isNorthSouth = true;
        // if we are north south x is a multple of 100, otherwise y is
        if (isNorthSouth) return new Point(street, address);
        else return new Point(address, street);
    }

    /**
     * Turn any point on the grid into an address
     *
     * @param point- point to convert to address
     * @return -string containing the street address
     */
    public static String getAddress(Point point) {
        if (point.x % 100 == 0) return point.y + " " + point.x + "th Avenue";
        else if (point.y % 100 == 0) return point.x + " " + point.y + "th Street";
        else return "Unknown Location";
    }
}
