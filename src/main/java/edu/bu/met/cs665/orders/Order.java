package edu.bu.met.cs665.orders;

import edu.bu.met.cs665.customers.Customer;
import edu.bu.met.cs665.stores.Store;
import edu.bu.met.cs665.stores.products.Product;

import java.util.ArrayList;
import java.util.List;

//orders made by customers
public class Order {

    public boolean isNeedsCold() {
        return needsCold;
    }

    public boolean isNeedsWarm() {
        return needsWarm;
    }


    public Customer getCustomer() {
        return customer;
    }


    public Order(Customer customer, Store store) {
        this.customer = customer;
        this.store = store;
        Order.currentOrderNumber++;
        this.orderNumber = currentOrderNumber;
    }

    public List<Product> getOrderItems() {
        return orderItems;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public Store getStore() {
        return store;
    }

    private Customer customer; //customer that placed the order
    private boolean needsWarm; //does it need to be kept warm
    private static int currentOrderNumber; //order number counter spread across all orders
    private int orderNumber; //this orders order number
    private List<Product> orderItems = new ArrayList<>(); //items ordered
    private boolean needsCold; //does it need to be kept cold
    private Store store; //store that has the order items.

    /**
     * create the object from the factory and add to the orderItems list
     *
     * @param product - what items is being orderd
     */
    public void addItem(Product product) {

        needsCold = product.isKeepCold();
        needsWarm = product.isKeepWarm();
        orderItems.add(product);
    }

    @Override
    public String toString() {
        //build out our list of order entries
        StringBuilder returnString = new StringBuilder();
        returnString.append("Ordered from: ");
        returnString.append(this.store.getName());
        returnString.append("\n");
        returnString.append("Located at: ");
        returnString.append(this.store.getAddress());
        returnString.append("\n");
        returnString.append("Customer Name: ");
        returnString.append(this.customer.getCustomerName());
        returnString.append("\n");
        returnString.append("Customer Address: ");
        returnString.append(this.customer.getAddress());
        returnString.append("\n");
        returnString.append("Customer's Birthday Today: ");
        returnString.append(this.customer.isBirthDay());
        returnString.append("\n");
        if (!this.customer.getNote().equals("")){
        returnString.append("Note: ");
        returnString.append(this.customer.getNote());
        returnString.append("\n");
        }


        String prefix = "";
        for (Product product : orderItems
                ) {
            returnString.append(prefix);
            returnString.append(product);
            prefix = "\n";

        }
        return returnString.toString();
    }
}
