package com.rishabhtech.dp.creational.builder;

// When you want to create an Object step by step.
// Removes the Telescoping Constructor problem.

/**
 * ============================= BUILDER DESIGN PATTERN =============================
 *
 * Category: Creational
 *
 * Intent:
 *   Construct a complex object step by step. The same construction process
 *   can create different representations (e.g., Flat, Duplex, Villa).
 *
 * Telescoping Constructor Problem (why Builder exists):
 *   Imagine a class with many optional fields:
 *     new House(4, 2, true, false, true, "red", 2, true) — unreadable!
 *   With Builder:
 *     House.builder().walls(4).doors(2).hasGarage(true).color("red").build() — clear!
 *
 * When to use:
 *   - Object has many fields, especially optional ones.
 *   - You want to avoid constructors with 5+ parameters.
 *   - The object should be immutable after construction.
 *   - You need to create different representations of the same object.
 *
 * Key Participants:
 *   1. Product        — the complex object being built (e.g., House).
 *   2. Builder        — interface defining the build steps.
 *   3. ConcreteBuilder — implements the steps for a specific type (e.g., FlatBuilder, DuplexBuilder).
 *   4. Director       — (optional) orchestrates the build steps in a specific order.
 *
 * Real-world examples:
 *   - StringBuilder in Java
 *   - Lombok's @Builder annotation
 *   - Spring Security's HttpSecurity.builder()
 *   - AlertDialog.Builder in Android
 *
 * Pros:
 *   - Readable object creation — each step is named.
 *   - Can create immutable objects.
 *   - Same build process → different representations.
 *
 * Cons:
 *   - More code (builder class per product).
 *   - Overkill for simple objects with few fields.
 *
 * =====================================================================================
 */

// ======================== PRODUCT ========================
// The complex object that we want to build step by step.
class House {

    private int walls;
    private int doors;
    private int windows;
    private boolean hasRoof;
    private boolean hasGarage;
    private String color;

    // Private constructor — only the Builder can create a House.
    private House() {}

    @Override
    public String toString() {
        return "House{walls=" + walls + ", doors=" + doors + ", windows=" + windows
                + ", hasRoof=" + hasRoof + ", hasGarage=" + hasGarage + ", color='" + color + "'}";
    }

    // ======================== STATIC INNER BUILDER ========================
    // The Builder provides named methods for each step.
    // Each setter returns `this` so methods can be chained (fluent API).
    static class Builder {

        private final House house;

        Builder() {
            house = new House();
        }

        Builder walls(int walls) {
            house.walls = walls;
            return this;
        }

        Builder doors(int doors) {
            house.doors = doors;
            return this;
        }

        Builder windows(int windows) {
            house.windows = windows;
            return this;
        }

        Builder hasRoof(boolean hasRoof) {
            house.hasRoof = hasRoof;
            return this;
        }

        Builder hasGarage(boolean hasGarage) {
            house.hasGarage = hasGarage;
            return this;
        }

        Builder color(String color) {
            house.color = color;
            return this;
        }

        // Final step — returns the fully constructed, immutable House.
        House build() {
            return house;
        }
    }
}

// ======================== DIRECTOR (Optional) ========================
// The Director knows HOW to build specific types of houses.
// It uses the Builder but defines the exact construction sequence.
// Client can skip the Director and use Builder directly if needed.
class HouseDirector {

    // Pre-defined construction for a Flat
    House buildFlat() {
        return new House.Builder()
                .walls(4)
                .doors(1)
                .windows(2)
                .hasRoof(true)
                .hasGarage(false)
                .color("White")
                .build();
    }

    // Pre-defined construction for a Duplex Home
    House buildDuplex() {
        return new House.Builder()
                .walls(8)
                .doors(3)
                .windows(6)
                .hasRoof(true)
                .hasGarage(true)
                .color("Brown")
                .build();
    }
}

// ======================== CLIENT ========================
public class BuilderLearn {
    static void main() {

        // Way 1: Using Builder directly (step by step, pick what you want)
        House customHouse = new House.Builder()
                .walls(6)
                .doors(2)
                .windows(4)
                .hasRoof(true)
                .hasGarage(true)
                .color("Blue")
                .build();
        System.out.println("Custom: " + customHouse);

        // Way 2: Using Director (pre-defined construction recipes)
        HouseDirector director = new HouseDirector();

        House flat = director.buildFlat();
        System.out.println("Flat:   " + flat);

        House duplex = director.buildDuplex();
        System.out.println("Duplex: " + duplex);
    }
}
