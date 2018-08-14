package edu.bu.met.cs665.stores;

import java.util.ArrayList;
import java.util.List;

//Uses Singleton Pattern because we only want one list of stores for the system
//Essentially is replacing an external storage system and holds all the stores in memory.
//not practical for real world implementation but works here
public class SystemStores {

    public List<Store> getStores() {
        return stores;
    }

    private static SystemStores storesInstance; //singleton instance




    private List<Store> stores; //list to hold all of our stores

    /**
     * Add a store to the system
     *
     * @param store - store to be added
     */
    public void addStore(Store store) {
        stores.add(store);
    }
    //prevent external construction
    private SystemStores() {
        stores= new ArrayList<>();
    }

    /**
     * Get the singleton instance of the store
     * synched since we are multithreaded, though not technically necessary for this implementation
     *
     * @return - SystemStores instance
     */
    public static synchronized SystemStores getInstance() {
        if (storesInstance == null) {
            storesInstance = new SystemStores();
        }
        return storesInstance;
    }

    @Override
    public String toString() {
        //return all the stores in the system
        StringBuilder returnString = new StringBuilder();
        for (Store store : stores
                ) {

            returnString.append(store.toString());
            returnString.append("\n");

        }
        return returnString.toString();
    }
}
