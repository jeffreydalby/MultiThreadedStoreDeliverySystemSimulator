package edu.bu.met.cs665.deliverysystem;

import edu.bu.met.cs665.customers.Customer;
import edu.bu.met.cs665.orders.Order;
import edu.bu.met.cs665.stores.Store;
import edu.bu.met.cs665.stores.StoreTypes;
import edu.bu.met.cs665.stores.products.Product;
import edu.bu.met.cs665.stores.products.ProductNames;
import edu.bu.met.cs665.stores.products.WarmFood;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDriverTest {

    private DeliveryDriver deliveryDriver;
    private Delivery delivery;
    private Order order;
    private Store store;
    private List<Product> stockItems = new ArrayList();
    private List<StoreTypes.Type> storeType = new ArrayList<>();
    private Customer customer;

    @Before
    public void setUp() {
        Product beijingBeef = new WarmFood(ProductNames.Names.beijingBeef,1);
        Product eggRoll = new WarmFood(ProductNames.Names.eggRoll,1);

        stockItems.add(beijingBeef);
        stockItems.add(eggRoll);
        storeType.add(StoreTypes.Type.chinese);
        customer = new Customer(new Point(2500,7354),"Test Customer");
        Store.StoreBuilder builder = new Store.StoreBuilder();
        store = builder.withName("Test Store")
                .withAddress("367 4000th Street")
                .withLocation(new Point(367,4000))
                .withStoreClassificationList(storeType)
                .build();

        deliveryDriver = new DeliveryDriver("Test Driver", new Point(10,145),true,true);
        order = new Order(customer,store);
        order.addItem(beijingBeef);
        delivery = new Delivery(deliveryDriver,order);


    }

    @Test
    public void updateDelivery() {

        deliveryDriver.updateDelivery(delivery);

    }

    @Test
    public void updateStatus() {
    }
}