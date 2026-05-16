package com.rishabhtech.dp.creational.factory;

//It is used when all the Objects creation and its Business logic we need to keep at one place.

/**
 * ============================= FACTORY DESIGN PATTERN =============================
 *
 * Category: Creational
 *
 * Intent:
 *   Define an interface for creating objects, but let a Factory class decide
 *   which concrete class to instantiate. The client never uses `new` directly —
 *   it delegates object creation to the factory.
 *
 * When to use:
 *   - You want to centralize and encapsulate object creation logic in one place.
 *   - The client doesn't know (or shouldn't care) which concrete class it needs.
 *   - Adding new types should not require changing client code (Open/Closed Principle).
 *   - Multiple classes share a common interface but differ in behavior.
 *
 * Key Participants:
 *   1. Product (interface/abstract)  — defines the contract (e.g., Shape).
 *   2. ConcreteProduct               — implements the contract (e.g., Square, Circle).
 *   3. Factory                       — creates and returns the right ConcreteProduct.
 *   4. Client                        — asks the Factory for an object, works with the Product interface.
 *
 * Real-world examples:
 *   - java.util.Calendar.getInstance()
 *   - java.text.NumberFormat.getInstance()
 *   - Spring's BeanFactory
 *   - JDBC: DriverManager.getConnection() returns a DB-specific Connection
 *
 * Factory vs Constructor:
 *   - Constructor: client does `new Square()` — tightly coupled to concrete class.
 *   - Factory: client does `factory.getShape("Square")` — loosely coupled via interface.
 *
 * Pros:
 *   - Loose coupling — client depends on interface, not concrete classes.
 *   - Single Responsibility — creation logic is in one place, easy to maintain.
 *   - Open/Closed — add new products without modifying client code.
 *
 * Cons:
 *   - Can lead to many factory classes if overused.
 *   - String-based selection (like here) has no compile-time safety — consider using Enums.
 *
 * =====================================================================================
 */

// ======================== PRODUCT INTERFACE ========================
// Defines the contract that all shapes must follow.
// The client programs to this interface, not to Square/Circle directly.
interface Shape {
    void computeArea();
}

// ======================== CONCRETE PRODUCTS ========================
// Each shape provides its own implementation of computeArea().
// New shapes (Triangle, Rectangle) can be added without changing existing code.

class Square implements Shape {
    @Override
    public void computeArea() {
        System.out.println("Square.computeArea()");
    }
}

class Circle implements Shape {
    @Override
    public void computeArea() {
        System.out.println("Circle.computeArea()");
    }
}

// ======================== FACTORY ========================
// Centralizes object creation — the client never does `new Square()` directly.
// To add a new shape, just add a new class + one more condition here.
class ShapeInstanceFactory {

    public Shape getShapeInstance(String value) {
        if (value.equals("Square"))
            return new Square();
        else if (value.equals("Circle"))
            return new Circle();
        // Returns null for unknown types — could also throw IllegalArgumentException
        return null;
    }
}

// ======================== CLIENT ========================
// The client only knows about Shape (interface) and the Factory.
// It has NO dependency on Square or Circle — fully decoupled.
public class Factory {
    static void main() {
        ShapeInstanceFactory factoryObj = new ShapeInstanceFactory();

        // Client asks the factory for shapes — doesn't care about which class is used.
        Shape square = factoryObj.getShapeInstance("Square");
        Shape circle = factoryObj.getShapeInstance("Circle");

        // Works with the Shape interface — polymorphism in action.
        square.computeArea();
        circle.computeArea();
    }
}
