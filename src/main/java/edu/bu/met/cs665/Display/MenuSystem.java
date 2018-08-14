package edu.bu.met.cs665.Display;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuSystem {

    public void displayWelcome() {
        Display.output("Welcome to the Delivery System Simulator.\r\n\r\n" +
                "This program simulates an online system where customers can place orders to various local businesses,\n" +
                "and then an independent delivery driver is assigned the order,\r\n" +
                "picks up the order from the store, and delivers it to the customer.\r\n");
    }

    public int requestNumStores(){
        Display.output("How many stores would you like to create?");
        return getIntegerInput();
    }

    public int requestNumCustomers(){
        Display.output("How many customers should there be?");
        return getIntegerInput();
    }

    public int requestNumDrivers(){
        Display.output("How many delivery drivers should be employed?");
        return getIntegerInput();
    }

    public int requestNumOrders(){
        Display.output("How many orders should be created?");
        return getIntegerInput();
    }

    public int requestTimeBetweenOrders(){
        Display.output("How many milliseconds should there be in between orders?");
        return getIntegerInput();
    }

    public int requestNumThreads(){
        Display.output("How many driver threads would you like to use?");
        return getIntegerInput();
    }

    private int getIntegerInput() {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                int returnNumber = scanner.nextInt();
                scanner.nextLine();
                return returnNumber;
            } catch (InputMismatchException ex) {
                Display.outputWithSeparator("Please enter an integer.");
            }
        }


    }

}
