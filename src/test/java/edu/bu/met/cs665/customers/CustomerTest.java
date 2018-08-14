package edu.bu.met.cs665.customers;

import org.junit.Test;

public class CustomerTest {

    @Test
    public void toStringTest() {
        //since we are creating random info can't really test this but want to at least outputWithSeparator to console to verify it works
        System.out.println(new Customer("Bob Smith"));
    }
}