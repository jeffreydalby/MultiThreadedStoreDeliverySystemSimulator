package edu.bu.met.cs665.geography;

import java.awt.*;

//helper method to calculate distance between to point on the grid
public class Distances {
    /**
     * REturns distance between two point
     *
     * @param point1 - first point
     * @param point2 - second point
     * @return - distance between the points
     */
    public static double getDistanceBetweenPoints(Point point1, Point point2) {
        return Math.sqrt(Math.pow((point2.x - point1.x), 2) + Math.pow(point2.y - point1.y, 2));
    }

}
