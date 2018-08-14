package edu.bu.met.cs665.deliverysystem;

//interface for observers
public interface Observer {
    void updateDelivery(Delivery delivery);
    void updateStatus();
}
