package edu.bu.met.cs665.simulator.namingsystem;

import edu.bu.met.cs665.stores.StoreTypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//controller that will create the right type of store name based on the
//store types that were passed to it.
public class StoreNamer {

    private Map<StoreTypes.Type, NameGenerator> behaviors = new HashMap<>();

    //all of the potential store namer behaviors
    public StoreNamer() {
        behaviors.put(StoreTypes.Type.pizza, new PizzaNameGenerator());
        behaviors.put(StoreTypes.Type.chinese, new ChineseNameGenerator());
        behaviors.put(StoreTypes.Type.flower, new FlowerNameGenerator());
        behaviors.put(StoreTypes.Type.frozenDinner, new FrozenDinnerNameGenerator());
        behaviors.put(StoreTypes.Type.desserts, new DessertsNameGenerator());
        behaviors.put(StoreTypes.Type.southWestern, new SouthwesternNamesGenerator());
        behaviors.put(StoreTypes.Type.officeSupply, new OfficeSupplyNameGenerator());
    }

    //we use a list to allow for hybrid stores
    public String getRandomName(List<StoreTypes.Type> generatorTypes) {
        NameGenerator generator;
        StringBuilder randomName = new StringBuilder();
        String myPrefix = ""; //standard means to not have extra character at the end
        for (StoreTypes.Type generatorType : generatorTypes
                ) {
            generator = behaviors.get(generatorType);
            randomName.append(myPrefix);
            myPrefix = " & ";
            randomName.append(generator.getName());
        }
        return randomName.toString();

    }
}
