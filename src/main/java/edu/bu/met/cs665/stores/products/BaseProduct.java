package edu.bu.met.cs665.stores.products;

//base class to products from
public class BaseProduct implements Product {
    public boolean isKeepCold() {
        return keepCold;
    }


    public boolean isKeepWarm() {
        return keepWarm;
    }


    public ProductNames.Names getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private boolean keepCold; //does this product need to be kept cold
    private boolean keepWarm; //does the product need to be kept warm?
    private ProductNames.Names productName; //name of this product
    private int quantity; //quantity for the order (could be used to track store inventory as well)

    BaseProduct() {
    }

    /**
     * Create a product
     *
     * @param keepCold    - must it be kept cold
     * @param keepWarm    - must it be kept warm
     * @param productName - name of the product
     * @param quantity    - quantity of the product
     */
    public BaseProduct(boolean keepCold, boolean keepWarm, ProductNames.Names productName, int quantity) {
        this.keepCold = keepCold;
        this.keepWarm = keepWarm;
        this.productName = productName;
        this.quantity = quantity;
    }

    //Return in the form af a cash register receipt.
    @Override
    public String toString() {
        return this.quantity + "x " + this.productName + (keepCold ? " *Cold Item" : "") + (keepWarm ? " *Warm Item" : "");
    }
}
