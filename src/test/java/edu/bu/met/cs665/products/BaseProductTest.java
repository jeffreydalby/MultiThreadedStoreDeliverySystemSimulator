package edu.bu.met.cs665.products;

import edu.bu.met.cs665.stores.products.BaseProduct;
import edu.bu.met.cs665.stores.products.ProductNames;
import org.junit.Assert;
import org.junit.Test;

public class BaseProductTest {

    //validate ir returns the proper string
    @Test
    public void toStringTest() {
        String productString = (new BaseProduct(ProductNames.Names.frozenDinner1.isKeepCold(), ProductNames.Names.frozenDinner1.isKeepWarm(), ProductNames.Names.frozenDinner1, 2)).toString();
        Assert.assertEquals("2x " + ProductNames.Names.frozenDinner1 + " *Cold Item",productString);
    }
}