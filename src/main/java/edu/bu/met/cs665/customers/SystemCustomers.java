package edu.bu.met.cs665.customers;

import java.util.ArrayList;
import java.util.List;

//Singleton Pattern because we only want one instance of our Customer list
//Place to hold all of our potential customers since we aren't using a database
public class SystemCustomers {

    private static SystemCustomers customersInstance;

    public List<Customer> getCustomers() {
        return customers;
    }

    private List<Customer> customers = new ArrayList<>();

    private SystemCustomers() {
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    //synced incase any of th threads try to manipulate the customer list at the same time
    public static synchronized SystemCustomers getInstance() {
        if (customersInstance == null) {
            customersInstance = new SystemCustomers();
        }
        return customersInstance;
    }

    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder();
        for (Customer customer : customers
                ) {
            returnString.append(customer.toString() + "\n");
        }
        return returnString.toString();
    }
}
