package edu.bu.met.cs665.simulator;

//Singleton..only one order simulator per instance

import edu.bu.met.cs665.Display.Display;
import edu.bu.met.cs665.customers.Customer;
import edu.bu.met.cs665.customers.SystemCustomers;
import edu.bu.met.cs665.deliverysystem.Delivery;
import edu.bu.met.cs665.deliverysystem.Dispatch;
import edu.bu.met.cs665.orders.Order;
import edu.bu.met.cs665.stores.Store;
import edu.bu.met.cs665.stores.SystemStores;
import edu.bu.met.cs665.stores.products.Product;
import edu.bu.met.cs665.stores.products.ProductNames;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

//The order simulator has the ability to automatically create random orders
//it can create as many as wanted spread out over a specified time interval between orders
//implemented as a singleton primarily as practice, in reality you could have multiple instances creating orders at different intervals
public class OrderSimulator implements Runnable {


    private static OrderSimulator orderSimulatorInstance; //singleton instance for the order
    private Thread orderSimulatorThread; //thread so we can shut the order system down

    private OrderSimulator() {
    } //private so can't be instantiated

    private int milliSecondsBetweenOrders; //milliseconds to pause before creating the next order
    private int numOrders; //number of orders to be created
    private boolean creatingOrders = true; //boolean to keep track if we are still creating orders

    public boolean isCreatingOrders() {
        return creatingOrders;
    }

    //get thread that is running this simulator
    public Thread getOrderSimulatorThread() {
        return this.orderSimulatorThread;
    }

    //get singleton instance
    public static synchronized OrderSimulator getInstance() {
        if (orderSimulatorInstance == null) orderSimulatorInstance = new OrderSimulator();
        return orderSimulatorInstance;
    }

    /**
     * Start up the order simulator
     *
     * @param numOrders                 - how many orders to create
     * @param millisecondsBetweenOrders - milliseconds between orders
     */
    void startSimulation(int numOrders, int millisecondsBetweenOrders) {
        this.numOrders = numOrders;
        this.milliSecondsBetweenOrders = millisecondsBetweenOrders;
        this.orderSimulatorThread = new Thread(OrderSimulator.getInstance());
        this.orderSimulatorThread.start();

    }

    //Order simulator process
    @Override
    public void run() {


        Random rnd = new Random();
        Dispatch systemDispatcher = Dispatch.getInstance();
        Delivery delivery;
        Customer customer;
        Store store;
        Order order;
        List<Product> orderItems;
        boolean isBirthday;


        //loop through the number of orders we want to create

        for (int i = 0; i < this.numOrders; i++) {

            if (Thread.currentThread().isInterrupted()) break;

            //pick a random store
            store = SystemStores.getInstance().getStores().get(rnd.nextInt(SystemStores.getInstance().getStores().size()));
            //get the items we can order from the store
            orderItems = store.getStockItems();
            //shuffle that list up so we can get some randomness;
            Collections.shuffle(orderItems);

            customer = SystemCustomers.getInstance().getCustomers().get(rnd.nextInt(SystemCustomers.getInstance().getCustomers().size()));

            order = new Order(customer, store);

            //randomly decide if it is the persons birthday 20% .
            isBirthday = rnd.nextInt(10) < 2;
            if (isBirthday && !customer.isBirthDay()) {
                //Add a gift box, making sure that we have one in the list of items to add
                if (orderItems.stream().anyMatch(item->item.getProductName().equals(ProductNames.Names.giftBox))) {
                    Product giftBox =orderItems.stream().filter(item -> item.getProductName().equals(ProductNames.Names.giftBox)).findFirst().orElse(null);
                    if (giftBox != null) {
                        giftBox.setQuantity(1);
                        order.addItem(giftBox);

                    }
                }
                customer.setBirthDay(true);

            }
            else if(isBirthday && customer.isBirthDay())
            {
                customer.setNote("Customer already received gift box with previous order.");
            }

            //add a random number of things to the order up
            //First filter out gift boxes since we don't want people to be able to just order them (they are a promotion given away)
            List<Product> tempOrderFromList = orderItems.stream().filter(items->items.getProductName()!= ProductNames.Names.giftBox).collect(Collectors.toList());
            int numOrderItems = (rnd.nextInt(tempOrderFromList.size())+1);
            for (int j = 0; j < numOrderItems; j++) {
                Product productToAdd = tempOrderFromList.get(j);
                //allow a random amount up to 5.
                productToAdd.setQuantity(rnd.nextInt(5) + 1);
                //don't add birthday box
                order.addItem(productToAdd);
            }

            systemDispatcher.placeOrder(order);

            //We are going to sleep for the requested time between orders
            try {
                Thread.sleep(milliSecondsBetweenOrders);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                Display.outputWithSeparator("Order System exited");
            }

        }
        creatingOrders = false;
        Display.outputWithSeparator("All orders created");
    }
}
