package edu.bu.met.cs665.stores.products;

public class ColdFood extends BaseProduct {

    /**
     * cold food object set super class to keepCold
     *
     * @param productType - type of cold food
     * @param quantity    - quantity of cold food
     */
    public ColdFood(ProductNames.Names productType, int quantity) {
        super(true, false, productType, quantity);
    }
}
