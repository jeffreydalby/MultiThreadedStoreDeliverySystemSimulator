package edu.bu.met.cs665.customers;

import edu.bu.met.cs665.geography.Address;

import java.awt.*;

//class to hold our various customer objects
public class Customer {
    public String getAddress() {
        return address;
    }

    public String getCustomerName() {
        return customerName;
    }
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private String address; //derived from the location
    private Point location; //a point on a 10,000 x 10,000 unit grid

    public Point getLocation() {
        return location;
    }

    public boolean isBirthDay() {
        return birthDay;
    }

    private String customerName; //Name

    private String note = "";
    public void setBirthDay(boolean birthDay) {
        this.birthDay = birthDay;
    }

    private boolean birthDay;

    Customer(String customerName) {
        this(Address.getRandomGridPoint(), customerName);

    }

    /**
     * Customer constructor
     *
     * @param location     - point on the grid to create the customers house
     * @param customerName - name of customer
     */
    public Customer(Point location, String customerName) {
        this.location = location;
        this.address = Address.getAddress(location);
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return this.customerName + "\n" + this.address;
    }
}
