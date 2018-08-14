package edu.bu.met.cs665.stores;

import edu.bu.met.cs665.stores.products.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

//Store objects for the system
public class Store {

    //public getters
    public List<Product> getStockItems() {
        return stockItems;
    }

    public Point getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    private void setStockItems(List<Product> stockItems) {
        this.stockItems = stockItems;
    }

    private void setLocation(Point location) {
        this.location = location;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setStoreClassification(List<StoreTypes.Type> storeClassification) {
        this.storeClassification = storeClassification;
    }

    private List<Product> stockItems = new ArrayList<>(); //items the store carries
    private Point location; //grid point of the store
    private String address; //Store's address (based on grid point)
    private String name; //name of the store
    private List<StoreTypes.Type> storeClassification; //type of item(s) store carries

    /**
     * Add item to stock
     * @param productToAdd - Product to add to inventory
     */
    public void addStockItem(BaseProduct productToAdd){
        stockItems.add(productToAdd);
    }

    /**
     * Builds out a menu/inventory list
     * @return - String containing the list of items the store carries
     */

    private String getMenu(){
        StringBuilder returnStringBuilder = new StringBuilder();
        returnStringBuilder.append("The items on our menu are:\n");
        //we are okay with the string concatination inside the lambda because it is easier to read.
        stockItems.forEach(item->returnStringBuilder.append(item.getProductName() + "\n"));
        return returnStringBuilder.toString().substring(0,returnStringBuilder.length()-1);
    }

    public static class StoreBuilder{

        private String name;
        private Point location;
        private String address;
        List<StoreTypes.Type> storeClassifications = new ArrayList<>();

        public StoreBuilder withName(String name) {
            this.name = name;
            return this;
        }


        public StoreBuilder withLocation(Point location) {
           this.location = location;
            return this;
        }


        public StoreBuilder withAddress(String address) {
            this.address = address;
            return this;
        }


        public StoreBuilder withStoreClassificationList(List<StoreTypes.Type> storeClassifications) {
            this.storeClassifications = storeClassifications;
            return this;
        }


        public Store build() {
            Store builtStore = new Store();
            builtStore.setStoreClassification(storeClassifications);
            if (!builtStore.storeClassification.isEmpty())
                builtStore.setStockItems(getMenuItems(this.storeClassifications));

            builtStore.setAddress(this.address);
            builtStore.setName(this.name);
            builtStore.setLocation(this.location);
            return builtStore;
        }


        /**
         * Factory Method that allows us to return any type of product based on the name from the products enum
         *
         * @param name     - name of the product selected from the enum
         * @param quantity - quantity needed
         * @return - return the Product object that was created
         */
        private Product productFactoryMethod(ProductNames.Names name, int quantity) {

            if (name.isKeepCold()) return new ColdFood(name, quantity);
            else if (name.isKeepWarm()) return new WarmFood(name, quantity);
            else if (name.getProductType() == ProductClassification.ProductType.reward) return new BirthDayBox();
            else return new StandardProduct(name, quantity);
        }

        //builds out menus, allowing for fusions of different menu types
        private List<Product> getMenuItems(List<StoreTypes.Type> storeType) {
            //List to hold our potential menu
            List<Product> menuList = new ArrayList<>();


            //build a list of types of items the store carries based on store classification
            List<ProductClassification.ProductType> typesOfMenuItems = new ArrayList<>();
            for (StoreTypes.Type typeOfStore : storeType
            ) {
                typesOfMenuItems.add(ProductClassification.ProductType.valueOf(typeOfStore.toString()));
            }
            //create the menu by filtering out just the types of items for this store
            //the use our factory to create the objects and add to the menu
            for (ProductClassification.ProductType productTypeOfMenu : typesOfMenuItems
            ) {
                Stream.of(ProductNames.Names.values())
                        .filter(name -> name.getProductType() == productTypeOfMenu)
                        .forEach(name -> menuList.add(this.productFactoryMethod(name, 0)));
            }
            //As part of the delivery program all stores have a gift box option for birthdays.
            menuList.add(this.productFactoryMethod(ProductNames.Names.giftBox, 1));

            return menuList;
        }

    }



    @Override
    public String toString() {
        StringBuilder storeTypes = new StringBuilder();
        String prefix ="";
        for (StoreTypes.Type storeType:storeClassification
             ) {
            storeTypes.append(prefix);
            storeTypes.append(storeType.toString());
            prefix = ",";
        }

        return "Name='" + name + '\'' +
                "\nAddress='" + address + '\'' +
                "\nStore item Types= " + storeTypes.toString() +
                "\n" + this.getMenu();
    }
}
