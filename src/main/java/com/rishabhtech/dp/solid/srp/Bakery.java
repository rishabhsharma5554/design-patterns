package com.rishabhtech.dp.solid.srp;

class BreadBaker
{
    void bakeBread()
    {
        System.out.println("Baking bread");
    }
}

class InventoryManager
{
    void checkInventory()
    {
        System.out.println("Checking inventory");
    }
}

class SupplyOrder
{
    void placeSupplyOrder()
    {
        System.out.println("Placing supply order");
    }
}

class CustomerService
{
    void handleCustomerRequest()
    {
        System.out.println("Handling customer request");
    }
}

class BakeryCleaning
{
    void cleanBakery()
    {
        System.out.println("Cleaning bakery");
    }
}

public class Bakery {

    public static void main(String[] args) {
        BreadBaker breadBaker = new BreadBaker();
        InventoryManager inventoryManager = new InventoryManager();
        SupplyOrder supplyOrder = new SupplyOrder();
        CustomerService customerService = new CustomerService();
        BakeryCleaning bakeryCleaning = new BakeryCleaning();

        // Each class focuses on its specific responsibility
        breadBaker.bakeBread();
        inventoryManager.checkInventory();
        supplyOrder.placeSupplyOrder();
        customerService.handleCustomerRequest();
        bakeryCleaning.cleanBakery();
    }
}
