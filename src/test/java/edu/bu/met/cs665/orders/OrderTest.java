package edu.bu.met.cs665.orders;

import edu.bu.met.cs665.Display.Display;
import edu.bu.met.cs665.customers.Customer;
import edu.bu.met.cs665.stores.Store;
import edu.bu.met.cs665.stores.StoreTypes;
import edu.bu.met.cs665.stores.products.Product;
import edu.bu.met.cs665.stores.products.ProductNames;
import edu.bu.met.cs665.stores.products.WarmFood;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OrderTest {

    @Test
    public void addItem() {

        List<StoreTypes.Type> storeType = new ArrayList<>();
        storeType.add(StoreTypes.Type.pizza);
        storeType.add(StoreTypes.Type.southWestern);
        String name = "Bob's Pizza And Tacos";
        Product pizza = new WarmFood(ProductNames.Names.pepperoniPizza,1);
        Product taco = new WarmFood(ProductNames.Names.taco,3);
        Store.StoreBuilder builder = new Store.StoreBuilder();
        Store testStore = builder.withName(name)
                .withAddress("234 10th Ave.")
                .withLocation(new Point(10,234))
                .withStoreClassificationList(storeType)
                .build();

        Order testOrder = new Order(new Customer(new Point(100,1345),"Test Customer" ), testStore);
        testOrder.addItem( pizza);
        testOrder.addItem(taco);
        Display.outputWithSeparator(testOrder.toString());


    }


}