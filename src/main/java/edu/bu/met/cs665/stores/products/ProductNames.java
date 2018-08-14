package edu.bu.met.cs665.stores.products;

public class ProductNames {
    //enum to avoid majic strings, in a real system we'd pull this from a database.
    //Adding a new item here will propagate it through the whole system, meaning
    //and a new productType of pizza and it will become a possible menu item for any pizza shop/order
    public enum Names {
        frozenDinner1("Frozen Dinner Special #1", true, false, ProductClassification.ProductType.frozenDinner),
        frozenDinner2("Frozen Dinner Special #2", true, false, ProductClassification.ProductType.frozenDinner),
        frozenDinner3("Frozen Dinner Special #3", true, false, ProductClassification.ProductType.frozenDinner),
        frozenDinner4("Frozen Dinner Special #4", true, false, ProductClassification.ProductType.frozenDinner),
        iceCreamCake("Ice Cream Cake", true, false, ProductClassification.ProductType.desserts),
        milkShake("Milk Shake", true, false, ProductClassification.ProductType.desserts),
        cake("Cake", false, false, ProductClassification.ProductType.desserts),
        chocolates("Box of Chocolates", false, false, ProductClassification.ProductType.desserts),
        roses("Bouguet of Roses", false, false, ProductClassification.ProductType.flower),
        daisies("Bouquet of Daises", false, false, ProductClassification.ProductType.flower),
        lilies("Bouquet of Lillies", false, false, ProductClassification.ProductType.flower),
        pepperoniPizza("Pepperoni Pizza", false, true, ProductClassification.ProductType.pizza),
        cheesePizza("Cheese Pizza", false, true, ProductClassification.ProductType.pizza),
        supremePizza("Supreme Pizza", false, true, ProductClassification.ProductType.pizza),
        sausagePizza("Sausage Pizza", false, true, ProductClassification.ProductType.pizza),
        breadSticks("Bread Sticks", false, true, ProductClassification.ProductType.pizza),
        taco("Taco", false, true, ProductClassification.ProductType.southWestern),
        fajitas("Fajitas", false, true, ProductClassification.ProductType.southWestern),
        burrito("Burrito", false, true, ProductClassification.ProductType.southWestern),
        nachos("Nachos", false, true, ProductClassification.ProductType.southWestern),
        orangeChicken("Orange Chicken", false, true, ProductClassification.ProductType.chinese),
        beijingBeef("Beijing Beef", false, true, ProductClassification.ProductType.chinese),
        friedRice("Fried Rice", false, true, ProductClassification.ProductType.chinese),
        eggRoll("Egg Roll", false, true, ProductClassification.ProductType.chinese),
        giftBox("Gift Box", false, false, ProductClassification.ProductType.reward),
        paper("Case of Paper", false, false, ProductClassification.ProductType.officeSupply),
        toner("Toner Cartridge", false, false, ProductClassification.ProductType.officeSupply),
        pencils("Pencils", false, false, ProductClassification.ProductType.officeSupply),
        pens("Pens Cartridge", false, false, ProductClassification.ProductType.officeSupply);

        private String nameToReturn;

        //returns whether this particular product needs to be kept cold
        public boolean isKeepCold() {
            return keepCold;
        }

        //returns if the product need to be kept warm
        public boolean isKeepWarm() {
            return keepWarm;
        }

        //reutnrs the product classification
        public ProductClassification.ProductType getProductType() {
            return productType;
        }

        private boolean keepCold; //does it need to be warm
        private boolean keepWarm; //does it need to be cold
        private ProductClassification.ProductType productType;  //type of product

        /**
         * Entends our names enum so we can have attributes for each named product
         *
         * @param nameToReturn - friendly name of the product
         * @param keepCold     -does it need to be kept cold?
         * @param keepWarm     - does it need to be kept warm?
         * @param ProductType  - what type of product is it (for classification into store types)
         */
        Names(String nameToReturn, boolean keepCold, boolean keepWarm, ProductClassification.ProductType ProductType) {
            this.nameToReturn = nameToReturn;
            this.keepCold = keepCold;
            this.keepWarm = keepWarm;
            this.productType = ProductType;
        }

        @Override
        public String toString() {
            return this.nameToReturn;
        }
    }

}
