package edu.bu.met.cs665.stores.products;

public interface Product {

    boolean isKeepCold(); //does it need to be kept cold

    boolean isKeepWarm(); //does it need to be kept warm

    int getQuantity(); //quantity needed

    void setQuantity(int quantity); //update the quantity as needed

    ProductNames.Names getProductName(); //return the name of the product
}
