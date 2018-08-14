# Store Delivery System Simulator

# Implementation 
This multi-threaded complete simulator is a complex simulation that is highly scalable, with the ability to automatically generate all the objects needed at run time.

## General Design
### Stores
Names of Stores are randomly generated based on the types of items they stock. Store names are generated using the "Strategy" Design pattern. 

A store can be any combination of two subtypes. They are created using the Factory Method pattern, and have a list of products they stock, which is also created using Factory Method.

### Users
Users of the system are generatated at run time with names generated randomly using the Strategy pattern, addresses are randomly assigned on a 1000 x 1000 block grid. 

### Delivery Drivers
Delivery Drivers are created at run time in their own thread and implement the observer side of the Observer pattern.  They run at default speed of 1 tick per seconds, with a travel distance of 300 blocks per tick (changable via a constant on the delivery driver object). The following attributes for their vehicle are set at the time of creation:

- Location - where on the city grid are they currently located
- hasCooler - does their vehicle have a cooler on board to keep items cold.  
- hasHeater - does their vehidle have a heater to keep products warm.
- name - auto-generated using the Strategy pattern

They also have the ability to store a "delivery item".  Each "tick" of their clock allows them to check if they have been assigned a new delivery, if so to first head to the appropriate store to pick up the item, and then head to the customer. For the sake of simplicty distances are rounded to city blocks.


### Orders

Orders are created by the Simulator from random stores, with random items being delivered to random customers.

### Dispatch

Dispatch acts as the main system- monitoring for incoming orders, finding the nearest driver with the equipment requirements to handle the order(needs to be kept cold or hot), and then creating a "Delivery" object and notifying the appropriate driver using tthe Observer pattern (drivers register with Dispatch when they start running).

In order to meet a system requirement the dispatch system kicks off a "Rush Hour" even based on the simulator's system timer. Dispatch also using the Observer pattern to request updates from all registered drivers as to their current status.
 
### Simulator
The Simulator is responsible for kicking off the creation of all of our objects, and starting the appropriate threads.

#### Order Simulator Sub-system
A sub-system of of the simulator is the order generation system.  This has the ability to create as many orders as specified and will submit them spread with a specified time between orders.

# Flexibility vs Complexity
This simulator has been designed to be as flexible as possible, with the trade off being complexity. Constants in the main method can be set to change the number of drivers, orders, stores, customers, and the time interval between orders and everything else will be handled automatically.

New "products" can simply be added to an enum of product and they will show up in the "menus" for each type of store automatically, which is a benefit of using factory patterns to create the objects, and Strategy patterns to control the behavior of the name generators.

Add a new store type is slightly more complex, since a new name generator would need to be created, but even then there is a minumal amount of work since it is only a matter of adding a new interface, and the type to a couple of enums.

All of this automation comes at the cost of a fairly complex system design, though code duplication has been avoided as much as possible (nearly entirely) by utilizing various design patterns (factories, strategy pattern, etc.) The design also makes use of a few singleton objects, primarily due to a desire to practice this technique.

# Design patterns:
## Observer Pattern
The dispatch system makes use of the Observer Pattern, with each delivery driver registering with the Dispatch object.  Dispatch then calls update methods on the driver to either notify them of a new delivery assigned to them, or to request a printout of a status update.
## Factory method pattern
Because the number of objects (drivers, stores, orders) can be set prior to running the simulation, it needed to be able to scale to potentially hundred or thousands of items.  The only practical  way to create each object was to use the Factory method pattern.
## Strategy Pattern
The behavior of the name generator only changes in as much as the type of names it needs to create, whether it is a person's name or a store's name. By using he Strategy pattern we can simply call the right naming behavior based on the type of object it is naming.
## Singleton Pattern
This pattern is used for the Dispatch object, Primary Simulator Object, Order Simulator, and Simulator Clock Ticker. We only need one of each object, and need to ensure that we grab the right information from them.  That being said, given the current "distaste" for singletons in the programming world, none of these had to be created this way and were done so as a way to practice this technique.


# Requirements as given
A consortium of shops in a large city has established an agreement with local independent van and
taxi drivers to deliver products from the stores to the customer destinations. When a store gets a
product delivery order, it creates a request which is broadcast to relevant drivers within a certain
distance from the store. A driver is then assigned based on the closest driver distance to the shop
and the customer is notified that a delivery has been scheduled.

• There 3 different kind of products 1. food (warm and frozen meals), 2. flower bouquets
and 3. gift chocolate boxes.

• Some of the food products are warm and require to be kept warm during transportation, and
some of the foods are frozen food and has to be cooled. Some of the vans have freezer on
board so that frozen food should be assigned only to them if the delivery distance is larger
than 2 miles.

• If the customer has birthday and orders any kind of products, we want to deliver as a birthday
present a chocolate boxes and a flower bouquet. This is our constrain that we want to deliver
both chocolate and flower together.

• In the event of high traffic in the city (like rush hours), all frozen food should be assigned to
the vans with freezers.

Implement the main delivery assignment system. Simulate and test your application using
random test objects of 10 drivers, 5 shops with 5 different types of products, and 10 customer
orders and 1 high traffic event. The distance between drivers can be implemented by using

