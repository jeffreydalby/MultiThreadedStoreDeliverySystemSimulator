package edu.bu.met.cs665.stores.products;

public interface ProductCreator {
    //base definition for the product factory
    BaseProduct createProduct(ProductNames.Names name, int quantity);
}
