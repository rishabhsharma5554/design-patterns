package com.rishabhtech.dp.creational.abstractfactory;
// It is a Factory of Factory
/**
 * ============================= ABSTRACT FACTORY DESIGN PATTERN =============================
 *
 * Category: Creational
 *
 * Intent:
 *   A "Factory of Factories" — provides an interface for creating families of
 *   related objects without specifying their concrete classes.
 *
 * Factory vs Abstract Factory:
 *   - Factory:          ONE factory creates different products (e.g., ShapeFactory → Square, Circle).
 *   - Abstract Factory: A super-factory creates DIFFERENT FACTORIES, each producing its own family
 *                        of products (e.g., EconomyCarFactory → Alto, Swift; LuxuryCarFactory → BMW).
 *
 * When to use:
 *   - You have families/groups of related products (e.g., Economy cars vs Luxury cars).
 *   - The client should work with any family without knowing the concrete classes.
 *   - You want to enforce that products from the same family are used together.
 *
 * Key Participants:
 *   1. AbstractFactory           — interface that declares factory methods (e.g., AbstractFactory).
 *   2. ConcreteFactory           — implements the interface for a specific family (e.g., EconomyCarFactory, LuxuryCarFactory).
 *   3. AbstractProduct           — interface for the product (e.g., Car).
 *   4. ConcreteProduct           — actual product implementations (e.g., EconomicCar1, LuxuryCar1).
 *   5. FactoryProducer (Client)  — decides which factory to create based on input.
 *
 * Flow in this example:
 *   Client → AbstractFactoryProducer → picks EconomyCarFactory or LuxuryCarFactory
 *         → chosen factory → creates the right Car based on price
 *
 * Real-world examples:
 *   - UI Toolkit: WindowsUIFactory → WindowsButton, WindowsCheckbox
 *                 MacUIFactory     → MacButton, MacCheckbox
 *   - JDBC: different DB drivers producing Connection, Statement, ResultSet families
 *   - Spring: different profile-based bean factories
 *
 * Pros:
 *   - Ensures products from the same family are compatible.
 *   - Isolates concrete classes from the client — loose coupling.
 *   - Easy to swap entire product families (just change the factory).
 *
 * Cons:
 *   - Adding a new product type to the family requires changing ALL factory interfaces.
 *   - Can become complex with many product families and types.
 *
 * =====================================================================================
 */

// ======================== FACTORY PRODUCER (Super Factory) ========================
// Decides which concrete factory to return based on the category.
// This is the "Factory of Factories" — the client starts here.
class AbstractFactoryProducer {

    public AbstractFactory getFactoryInstance(String value) {
        if (value.equalsIgnoreCase("Economic"))
            return new EconomyCarFactory();
        else if (value.equalsIgnoreCase("Luxury") || value.equalsIgnoreCase("Premium"))
            return new LuxuryCarFactory();
        return null;
    }
}

// ======================== ABSTRACT FACTORY (Interface) ========================
// Declares the factory method that each concrete factory must implement.
// Each factory produces Cars, but the TYPE of car depends on the factory.
interface AbstractFactory {
    Car getInstance(int price);
}

// ======================== CONCRETE FACTORIES ========================
// Each factory is responsible for creating products from its own family.

// Economy family — produces budget-friendly cars
class EconomyCarFactory implements AbstractFactory {

    @Override
    public Car getInstance(int price) {
        if (price <= 300000)       // e.g., Alto 800
            return new EconomicCar1();
        else if (price > 300000)   // e.g., Swift
            return new EconomicCar2();
        return null;
    }
}

// Luxury family — produces premium cars
class LuxuryCarFactory implements AbstractFactory {

    @Override
    public Car getInstance(int price) {
        if (price >= 3000000)      // e.g., BMW
            return new LuxuryCar1();
        return null;
    }
}

// ======================== ABSTRACT PRODUCT ========================
// Common interface for all cars — client programs to this, not to concrete classes.
interface Car {
    int getTopSpeed();
}

// ======================== CONCRETE PRODUCTS ========================
// Actual car implementations belonging to different families.

class EconomicCar1 implements Car {  // e.g., Alto 800

    @Override
    public int getTopSpeed() {
        return 100;
    }
}

class EconomicCar2 implements Car {  // e.g., Swift

    @Override
    public int getTopSpeed() {
        return 150;
    }
}

class LuxuryCar1 implements Car {    // e.g., BMW

    @Override
    public int getTopSpeed() {
        return 300;
    }
}

// ======================== CLIENT ========================
// The client only interacts with AbstractFactoryProducer, AbstractFactory, and Car.
// It has NO direct dependency on EconomicCar1, LuxuryCar1, etc.
public class AbstractFactoryLearn {
    static void main() {
        AbstractFactoryProducer factory = new AbstractFactoryProducer();

        // Step 1: Get the Economy factory
        // Step 2: Ask it to create a car based on price
        AbstractFactory economic = factory.getFactoryInstance("Economic");
        Car alto800 = economic.getInstance(50);
        System.out.println("Top Speed: " + alto800.getTopSpeed());  // 100

        // Step 1: Get the Luxury factory
        // Step 2: Ask it to create a car based on price
        AbstractFactory luxury = factory.getFactoryInstance("luxury");
        Car gt8 = luxury.getInstance(300000000);
        System.out.println("Top Speed: " + gt8.getTopSpeed());      // 300
    }
}
